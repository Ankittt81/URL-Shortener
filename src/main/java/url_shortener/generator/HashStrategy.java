package url_shortener.generator;

import org.springframework.stereotype.Component;

@Component("HashStrategy")
public class HashStrategy implements ShortCodeGeneratorStrategy{
    private RandomStrategy  randomStrategy;
    public HashStrategy(){
        randomStrategy = new RandomStrategy();
    }

    @Override
    public String generateShortCode(String longUrl) {
        long num = longUrl.hashCode() & 0xffffffffL;  //to handle negative we use 0xffff... due to which int convert into long
        StringBuilder sb=new StringBuilder();
        if(num==0) return "0";
        while(num>0){
            int rem=(int)(num%62);
            if(rem>=0 && rem<=9){
                sb.append(rem);
            }
            else if(rem>=10 && rem<=35){
                char ch=(char)(rem-10+'a');
                sb.append(ch);
            }
            else{
                char ch=(char)(rem-36+'A');
                sb.append(ch);
            }
            num=num/62;
        }
        String shortCode=sb.reverse().toString();
        return shortCode;
    }

    @Override
    public String generateAnotherCode(String longUrl) {
        String suffix=randomStrategy.generateShortCode(longUrl);
        return generateShortCode(longUrl+suffix);
    }
}
