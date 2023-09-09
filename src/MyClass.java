import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class MyClass {
    private Scanner scanner;
    private Boolean flag;

    public void start(){
        scanner = new Scanner(System.in);
        flag = true;


        while (flag){
            System.out.println("Введите через пробел: Фамилию, Имя, Отчество, дату рождения (в формате dd.mm.yyyy), номер телефона (только числа без пробелов) и пол (f - женский, m - мужской) Для выхода введите - Выход");

            String[] inputData = ((scanner.nextLine().split(" ")));
            int verificationCode = checkText(inputData);
            for (int i = 0; i < inputData.length; i++){
                if(inputData[i].equalsIgnoreCase("Выход")){
                    flag = false;
                    System.out.println("До скорой встречи!");
                    break;
                }


            }
            if (!flag){
                continue;
            }


            if(verificationCode == 0){
                System.out.println("Введено недостаточно данных!");
                continue;

            }
            else if(verificationCode == 1){
                System.out.println("Превышено количество данных!");
                continue;
            }
            else {
                try {
                    parsingText(inputData);
                } catch (IllegalStringFormatException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    continue;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    continue;
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    continue;
                }
            }

            System.out.println(saveText(inputData));



        }
    }




    private int checkText(String [] text){
        int check;
        if(text.length < 6){
            check = 0;
        }
        else if(text.length > 6){
            check = 1;
        }
        else{
            check = 2;
        }
        return check;

    }

    private void parsingText(String[] inputText) throws IllegalStringFormatException, ParseException, NumberFormatException {
        String surname;
        String name;
        String patronymic;
        SimpleDateFormat date = new SimpleDateFormat ("dd.MM.yyyy");
        int phoneNumber;
        String gender;

        surname = inputText[0];
        for(char c : surname.toCharArray())
            if(Character.isDigit(c)){
                throw new IllegalStringFormatException("Фамилия введена некорректно");
            }

        name = inputText[1];
        for(char c : name.toCharArray())
            if(Character.isDigit(c)){
                throw new IllegalStringFormatException("Имя введено некорректно");
            }
        patronymic = inputText[2];
        for(char c : patronymic.toCharArray())
            if(Character.isDigit(c)){
                throw new IllegalStringFormatException("Отчество введено некорректно");
            }
        date.parse(inputText[3]);


        phoneNumber = Integer.parseInt(inputText[4]);

        if(!inputText[5].equals("m") && !inputText[5].equals("f")){
            throw new IllegalStringFormatException( "Пол введен некорректно!");
        }

    }

    private String saveText(String[] inputText)  {
        String fileName =inputText[0];
        StringBuilder str = new StringBuilder();
        if(inputText.length > 0){
            for (int i = 0; i < inputText.length; i++) {
                str.append("<");
                str.append(inputText[i]);
                str.append(">");
            }
            str.append("\n");

        }
        try(FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(str.toString());
            writer.flush();
        }
        catch(IOException ex){
            ex.printStackTrace();
            return ex.getMessage();
        }
        return "Данные успешно сохранены";

    }


    public class IllegalStringFormatException extends RuntimeException {

        public IllegalStringFormatException(String message) {
            super(message);

        }
    }


}
