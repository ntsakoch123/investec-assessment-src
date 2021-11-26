package za.co.investec.assessment.clientmanagement.presentation.api.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdNumberValidatorTest {

    @Test
    public  void test_when_idNumber_isValid_then_returnTrue(){
        assertTrue(new IdNumberValidator().isValid("8605065397083",null));
    }

  @Test
    public  void test_when_idNumber_isNotValid_then_returnFalse(){
        assertFalse(new IdNumberValidator().isValid("8605065397081",null));
    }
}