package utils;

public class DinhDangString {
    public static String boKhoangTrang(String input){
        if(input == null){
            return null;
        }
        return input.replaceAll("\\s","");
    }
    public static String dinhDangTen(String input){
        if (input == null || input.isEmpty()) {
            return input;
        }
        input =  input.replaceAll("\\s+"," ");

        String[] words = input.split(" ");
        String result = "";
        for (String word : words){
            result += Character.toString(word.charAt(0)).toUpperCase();
            if(word.length() > 1){
                result += word.substring(1,word.length()).toLowerCase();
            }
            result +=  " ";
        }
        return  result.trim();
    }
    public  static String vanBanDep(String input){
        if(input == null)
            return input;
        return input.replaceAll("\\s+"," ").trim();
    }

}
