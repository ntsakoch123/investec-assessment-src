package za.co.investec.assessment.clientmanagement.presentation.api.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class IdNumberValidator implements ConstraintValidator<IdNumber, String> {

    @Override
    public boolean isValid(String idNumber, ConstraintValidatorContext constraintValidatorContext) {
        return validate(idNumber);
    }

    /*
     * ID Number format
     * {YYMMDD}{G}{SSS}{C}{A}{Z} :8605065397083
     * YYMMDD: Date of birth: 860506
     * G : Gender. 0-4 Female; 5-9 Male: 5
     * SSS : Sequence No. for DOB/G combination: 397
     * C : Citizenship. 0 SA; 1 Other. 0
     * A : Usually 8, or 9 (can be other values): 8
     * Z : Control digit: 3
     */

    private static boolean validate(String idNumber) {
        log.info("Validating IdNumber: {}", idNumber);
        if(idNumber == null) return  false;
        StringBuilder value = new StringBuilder(idNumber);
        if (value.length() != 13) {
            log.info("Invalid length");
            return false;
        }
        if (!isMonthValid(idNumber)) {
            log.info("Invalid Month");
            return false;
        }
        int dayOfMonth = Integer.parseInt(value.substring(4, 6));
        if (!(dayOfMonth >= 1 && dayOfMonth <= 31)) {
            log.info("Invalid dayOfMonth");
            return false;
        }

        boolean result = validateChecksum(idNumber);
        String message = result ? "idNumber: {} validated successfully." : "idNumber: {} validation failed.";
        log.info(message, idNumber);
        return  result;
    }

    private static boolean isMonthValid(String idNumber) {
        int month;
        for (month = 0; month < idNumber.length(); ++month) {
            char day = idNumber.charAt(month);
            if (day < 48 || day > 57) {
                return false;
            }
        }
        month = Integer.parseInt(idNumber.substring(2, 4));
        return month >= 1 && month <= 12;
    }

    private static boolean validateChecksum(String idNumber) {
        /*Given ID Number is 8605065397083
         *step 1: Add all the digits of the ID number in the odd positions (except for the last number, which is the control digit):
         *         8+0+0+5+9+0 = 22
         */
        int sumStep1 = 0;
        for (int d = 0; d < idNumber.length() - 1; d += 2) {
            sumStep1 += Integer.parseInt(String.valueOf(idNumber.charAt(d)));
        }
        log.debug("Step 1: {}", sumStep1);

        /*
         *Step 2: Take all the even digits as one number and multiply that by 2:
         *           656378 * 2 = 1312756
         */
        StringBuilder evenDigits = new StringBuilder();
        int multiplyStep2;
        for (multiplyStep2 = 1; multiplyStep2 < idNumber.length(); multiplyStep2 += 2) {
            evenDigits.append(idNumber.charAt(multiplyStep2));
        }
        multiplyStep2 = Integer.parseInt(evenDigits.toString()) * 2;
        log.debug("Step 2: {}", multiplyStep2);

        /*
         *Step 3:  Add the digits of the number in step 2 together
         *           1+3+1+2+7+5+6 = 25
         */
        String step2Digits = String.valueOf(multiplyStep2);
        int sumStep3 = 0;
        int step2Digit;
        for (step2Digit = 0; step2Digit < step2Digits.length(); ++step2Digit) {
            sumStep3 += Integer.parseInt(String.valueOf(step2Digits.charAt(step2Digit)));
        }
        log.debug("Step 3: {}", sumStep3);

        /*
         * Step 4: Add the answer of Step 3 to the answer of Step 1
         *           22+25 = 47
         */
        step2Digit = sumStep1 + sumStep3;
        log.debug("Step 4: {}", step2Digit);

        /*
         * Step 5:  Subtract the second digit answer in Step 4 from 10, this number should now equal the control character(last digit) and check citizenship status
         *          10-7 = 3 = control character (3)
         */

        //Step 5 get second digit by remainder: ie, 47%10 = 7 whereby 47 was the outcome from previous step and 10 is mathematics constant for getting a remainder out of any given number
        int secondDigitOfStep4 = step2Digit % 10;
        log.debug("Step 5[secondDigitOfStep4]: {}", secondDigitOfStep4);

        // answer for below statement should be equal to last digit(3)
        int step5Subtraction = (10 - secondDigitOfStep4) ;
        log.debug("Step 5[step5Subtraction]:10-{}={}", secondDigitOfStep4, step5Subtraction);

        int controlDigit = Integer.parseInt(String.valueOf(idNumber.charAt(idNumber.length() - 1)));
        log.debug("Step 5[controlDigit]: {}", controlDigit);
        int citizenShipStatus = Integer.parseInt(String.valueOf(idNumber.charAt(10)));
        log.debug("Step 5[citizenShipStatus]:{} ", controlDigit);
        //Compare subtraction above to the control digit&& check citizenShip status: 1 is SA permanent residence and 0 is natural residence
        return step5Subtraction == controlDigit &&  citizenShipStatus == 0 || citizenShipStatus == 1;
    }

}
