package utils;

public class MoneyFormatter {
  public static long convertToMoneyFormat(FormatType formatType,String str){
        long money = 0;
        try{
            money = Long.parseLong(str);
        }catch (NumberFormatException e){
            if(formatType == FormatType.CO_XU_LY_NGOAI_LE)
                throw e;
        }
        return  money;
    }

}
