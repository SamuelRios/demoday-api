// package com.demodayapi;
// import com.demodayapi.services.DemodayService;



// import java.time.LocalDate;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import com.demodayapi.models.Demoday;

// @SpringBootTest
// class DemodayApplicationTests {

//     @Test
//     void contextLoads() {
//     }

//     @Test
//     public void testValidateBiggestInitDate() {
//         Demoday demoday = new Demoday();
//         demoday.setPhaseOneInit(LocalDate.of(2024, 4, 1));
//         demoday.setPhaseTwoInit(LocalDate.of(2024, 5, 1));
//         demoday.setPhaseThreeInit(LocalDate.of(2024, 6, 1));
//         demoday.setPhaseFourInit(LocalDate.of(2024, 7, 1));

//         boolean result = ValidateBiggestInitDate(demoday) ;
//     }

//     @Test
//     public void testValidateBiggestEndDate() {
//         Demoday demoday = new Demoday();
//         demoday.setPhaseOneEnd(LocalDate.of(2024, 4, 30));
//         demoday.setPhaseTwoEnd(LocalDate.of(2024, 5, 30));
//         demoday.setPhaseThreeEnd(LocalDate.of(2024, 6, 30));
//         demoday.setPhaseFourEnd(LocalDate.of(2024, 7, 30));

//         assert ValidateBiggestEndDate(demoday) == true : "Teste falhou para ValidateBiggestEndDate";
//     }

//     @Test
//     public void testValidateBiggestBetweenInitEnd() {
//         Demoday demoday = new Demoday();
//         demoday.setPhaseOneEnd(LocalDate.of(2024, 4, 30));
//         demoday.setPhaseTwoInit(LocalDate.of(2024, 5, 1));
//         demoday.setPhaseTwoEnd(LocalDate.of(2024, 5, 30));
//         demoday.setPhaseThreeInit(LocalDate.of(2024, 6, 1));
//         demoday.setPhaseThreeEnd(LocalDate.of(2024, 6, 30));
//         demoday.setPhaseFourInit(LocalDate.of(2024, 7, 1));

//         assert ValidateBiggestBetweenInitEnd(demoday) == true : "Teste falhou para ValidateBiggestBetweenInitEnd";
//     }

//     // Os métodos ValidateBiggestInitDate, ValidateBiggestEndDate e ValidateBiggestBetweenInitEnd devem ser definidos aqui

// }
