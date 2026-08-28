package com.happypets.app_veterinaria_backend.seeder;

import org.springframework.stereotype.Component;

//Principal class to run the seeders for the database
@Component
public class SeederRunner {

    //TODO
   // private final UserSeeder userSeeder;


    // TODO: @Transactional
    //Principal method to run the seeders
    public void run() {

        System.out.println("================================");
        System.out.println("Starting run seeders...");
        System.out.println("================================");

        //TODO
        //userSeeder.run();


        System.out.println("================================");
        System.out.println("Seeders finished.");
        System.out.println("================================");
    }
}
