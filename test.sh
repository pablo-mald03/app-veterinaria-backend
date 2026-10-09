#!/usr/bin/env bash
# Prueba de humo del módulo de vacunación: vacunas, carnet y dosis.
# Uso:  TOKEN="eyJ..." ./scripts/probar_vacunacion.sh

BASE="${BASE:-http://localhost:8080/api/v1}"
TOKEN="${TOKEN:-}"
VACCINES_PATH="${VACCINES_PATH:-/vaccines}"
CARDS_PATH="${CARDS_PATH:-/vaccination-cards}"
RECORDS_PATH="${RECORDS_PATH:-/vaccination-records}"
PET_ID="${PET_ID:-$((RANDOM + 1000))}"   # cámbialo si tienes FK a pets
DOCTOR_ID="${DOCTOR_ID:-1}"              # cámbialo si tienes FK a users
SUFIJO="$(date +%s)"

OK=0
FALLOS=0
STATUS=""
BODY=""
TMP="$(mktemp)"
trap 'rm -f "$TMP"' EXIT

command -v jq >/dev/null || { echo "Falta jq: sudo pacman -S jq"; exit 1; }

req() { # metodo ruta [json]
  local metodo="$1" ruta="$2" datos="${3:-}"
  local args=(-s -o "$TMP" -w '%{http_code}' -X "$metodo" "$BASE$ruta" -H 'Content-Type: application/json')
  [[ -n "$TOKEN" ]] && args+=(-H "Authorization: Bearer $TOKEN")
  [[ -n "$datos" ]] && args+=(-d "$datos")
  STATUS="$(curl "${args[@]}")"
  BODY="$(cat "$TMP")"
}

verificar() { # descripcion esperado obtenido
  if [[ "$2" == "$3" ]]; then
    echo "  ✅ $1"; OK=$((OK + 1))
  else
    echo "  ❌ $1  (esperado: $2 | obtenido: $3)"; FALLOS=$((FALLOS + 1))
  fi
}

verificar_2xx() { # descripcion
  if [[ "$STATUS" == 2* ]]; then
    echo "  ✅ $1 (HTTP $STATUS)"; OK=$((OK + 1))
  else
    echo "  ❌ $1 (HTTP $STATUS)"; echo "     $BODY"; FALLOS=$((FALLOS + 1))
  fi
}

fecha() { date -d "$1" +%F; }   # fecha "+21 days" -> 2026-10-29

echo "== Base: $BASE | mascota: $PET_ID | doctor: $DOCTOR_ID =="

# ---------------------------------------------------------------
echo; echo "1) Crear vacunas"
req POST "$VACCINES_PATH" "{\"name\":\"Triple felina $SUFIJO\",\"description\":\"Esquema inicial\",\"species\":\"GATO\",\"dosesRequired\":3,\"intervalDays\":21,\"status\":true}"
verificar_2xx "Vacuna de 3 dosis creada"
if [[ "$STATUS" == "401" || "$STATUS" == "403" ]]; then
  echo "     Sin autorización: pasa un token válido con TOKEN=..."; exit 1
fi
ID_VAC3="$(echo "$BODY" | jq -r '.idVaccine // .id // empty')"

req POST "$VACCINES_PATH" "{\"name\":\"Rabia $SUFIJO\",\"description\":\"Esquema de 2 dosis\",\"species\":\"PERRO\",\"dosesRequired\":2,\"intervalDays\":21,\"status\":true}"
verificar_2xx "Vacuna de 2 dosis creada"
ID_VAC2="$(echo "$BODY" | jq -r '.idVaccine // .id // empty')"

[[ -z "$ID_VAC3" || -z "$ID_VAC2" ]] && { echo "No pude leer el id de la vacuna (revisa el JSON de respuesta)"; exit 1; }
echo "     idVaccine: $ID_VAC3 (3 dosis) | $ID_VAC2 (2 dosis)"

# ---------------------------------------------------------------
echo; echo "2) Carnet de vacunación"
req POST "$CARDS_PATH/pet/$PET_ID"
verificar_2xx "Carnet creado"
ID_CARD="$(echo "$BODY" | jq -r '.idCard // empty')"
echo "     idCard: $ID_CARD"

req POST "$CARDS_PATH/pet/$PET_ID"
verificar "Crear de nuevo devuelve el mismo carnet (no duplica)" "$ID_CARD" "$(echo "$BODY" | jq -r '.idCard // empty')"

req GET "$CARDS_PATH/pet/$PET_ID"
verificar "Consultar carnet por mascota" "$PET_ID" "$(echo "$BODY" | jq -r '.idPet // empty')"

# ---------------------------------------------------------------
echo; echo "3) Dosis del esquema de 3 dosis (intervalo 21 días)"
registrar() { # idVaccine fechaAplicacion
  req POST "$RECORDS_PATH" "{\"idCard\":$ID_CARD,\"idVaccine\":$1,\"idDoctor\":$DOCTOR_ID,\"applicationDate\":\"$2\",\"batchNumber\":\"LOTE-$SUFIJO\",\"notes\":\"prueba\"}"
}

D1="$(fecha 'today')"; D2="$(fecha '+21 days')"; D3="$(fecha '+42 days')"

registrar "$ID_VAC3" "$D1"
verificar_2xx "Dosis 1 registrada"
verificar "  doseNumber = 1" "1" "$(echo "$BODY" | jq -r '.doseNumber')"
verificar "  nextDoseDate = $D2" "$D2" "$(echo "$BODY" | jq -r '.nextDoseDate')"

registrar "$ID_VAC3" "$D2"
verificar_2xx "Dosis 2 registrada"
verificar "  doseNumber = 2" "2" "$(echo "$BODY" | jq -r '.doseNumber')"
verificar "  nextDoseDate = $D3" "$D3" "$(echo "$BODY" | jq -r '.nextDoseDate')"

registrar "$ID_VAC3" "$D3"
verificar_2xx "Dosis 3 registrada"
verificar "  doseNumber = 3" "3" "$(echo "$BODY" | jq -r '.doseNumber')"
verificar "  nextDoseDate = null (esquema completo)" "null" "$(echo "$BODY" | jq -r '.nextDoseDate')"

registrar "$ID_VAC3" "$(fecha '+63 days')"
verificar "Dosis 4 rechazada (esquema completo, esperado 409)" "409" "$STATUS"

# ---------------------------------------------------------------
echo; echo "4) Dosis vencida debe aparecer en pendientes"
VENCIDA="$(fecha '-30 days')"        # +21 días => venció hace 9 días
registrar "$ID_VAC2" "$VENCIDA"
verificar_2xx "Dosis 1 de Rabia registrada hace 30 días"
ID_REC_VENCIDO="$(echo "$BODY" | jq -r '.idRecord')"
verificar "  nextDoseDate = $(fecha '-9 days')" "$(fecha '-9 days')" "$(echo "$BODY" | jq -r '.nextDoseDate')"

req GET "$RECORDS_PATH/pending?days=7"
verificar_2xx "Consulta de pendientes"
verificar "  incluye la dosis vencida" "1" "$(echo "$BODY" | jq --argjson id "$ID_REC_VENCIDO" '[.[] | select(.idRecord == $id)] | length')"
verificar "  NO incluye dosis ya reemplazadas (esquema de 3 dosis)" "0" "$(echo "$BODY" | jq --argjson c "$ID_CARD" --argjson v "$ID_VAC3" '[.[] | select(.idCard == $c and .idVaccine == $v)] | length')"

# ---------------------------------------------------------------
echo; echo "5) Historial del carnet"
req GET "$RECORDS_PATH/card/$ID_CARD"
verificar_2xx "Consulta de dosis por carnet"
verificar "  4 dosis en total (3 + 1)" "4" "$(echo "$BODY" | jq 'length')"

# ---------------------------------------------------------------
echo; echo "6) Errores esperados"
req POST "$RECORDS_PATH" "{\"idCard\":999999,\"idVaccine\":$ID_VAC3,\"idDoctor\":$DOCTOR_ID,\"applicationDate\":\"$D1\"}"
verificar "Carnet inexistente (esperado 404)" "404" "$STATUS"

req POST "$RECORDS_PATH" "{\"idCard\":$ID_CARD,\"idVaccine\":999999,\"idDoctor\":$DOCTOR_ID,\"applicationDate\":\"$D1\"}"
verificar "Vacuna inexistente (esperado 404)" "404" "$STATUS"

req POST "$RECORDS_PATH" "{\"idCard\":$ID_CARD}"
verificar "Body incompleto (esperado 400)" "400" "$STATUS"

# ---------------------------------------------------------------
echo; echo "== Resultado: $OK correctas, $FALLOS con fallo =="
[[ "$FALLOS" -eq 0 ]]