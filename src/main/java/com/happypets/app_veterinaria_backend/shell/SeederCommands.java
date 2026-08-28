package com.happypets.app_veterinaria_backend.shell;

import com.happypets.app_veterinaria_backend.seeder.SeederRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.CommandGroup;
import org.springframework.stereotype.Component;

@Component
@CommandGroup(
        name = "Seeder Commands",
        prefix = "seed"
)
@RequiredArgsConstructor
public class SeederCommands {

    private final SeederRunner seederRunner;

    @Command(
            name = "run",
            description = "Ejecuta todos los seeders"
    )
    public String run() {

        seederRunner.run();

        return "Seeders ejecutados correctamente.";
    }
}