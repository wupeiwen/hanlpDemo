import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BD on 2017/5/8.
 */
public class main {
//    正则匹配
    public static boolean check(String rule, String data){
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    public static void main(String[] args) {

        String testCase = "詹金彪的身份证号码为342861198310274835，出生日期为1983年10月27日，家庭住址为安徽省宿松县二郎镇闵咀村詹屋15号，联系方式为15868194743，固定电话为0571-80892278，现在暂住地址是浙江省杭州市拱墅区温州路普金花园1幢1单元1201室，因房屋漏水一事与楼上邻居发生口角冲突，现在需要楼上邻居赔偿因房屋漏水导致墙面损害2000元，并且当面道歉。";

//        标准分词
//        System.out.println("标准分词:"+StandardTokenizer.segment(testCase));

//        NLP分词
//        System.out.println("NLP分词:"+NLPTokenizer.segment(testCase));

//        索引分词
//        List<Term> termList = IndexTokenizer.segment(testCase);
//        for (Term term : termList)
//        {
//            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
//        }

//        N-最短路径分词
//        Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
//        Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
//        System.out.println("N-最短分词：" + nShortSegment.seg(testCase) + "\n最短路分词：" + shortestSegment.seg(testCase));

//        CRF分词(使用内置数据包的Maven版本构建项目，CRF分词、依存句法分析无法使用)
//        Segment crfSegment = new CRFSegment().enablePartOfSpeechTagging(true);
//        System.out.println(crfSegment.seg(testCase));

        boolean existPhone = false;
        boolean existIDcard = false;
        boolean existName = false;
        boolean existAddress = false;
//        匹配姓名、地址、电话、身份证号
        for (Term term : StandardTokenizer.segment(testCase))
        {
            String temp[] =term.toString().split("/");
            switch (temp[1]){
                case "nr":
                    existName=true;
//                    System.out.println("姓名："+temp[0]);
                    break;
                case "ns":
                    existAddress=true;
//                    System.out.println("地址："+temp[0]);
                    break;
                case "m":
                    if (check("^0\\d{2,3}\\d{7,8}$|^1[358]\\d{9}$|^147\\d{8}",temp[0])){
//                        正则表达式匹配手机号
                        existPhone=true;
//                        System.out.println("手机号："+temp[0]);
                    }else if (check("([1-9]\\d{14}|[1-9]\\d{14}\\d{2}[0-9x])$",temp[0])){
//                        正则表达式匹配身份证号
                        existIDcard=true;
//                        System.out.println("身份证号："+temp[0]);
                }
                    break;
            }
        }
        String result="检测到可能存在以下敏感信息: ";
        if (existPhone||existIDcard||existName||existAddress){
            if(existName){
                result+="姓名 ";
            }
            if(existAddress){
                result+="地址 ";
            }
            if(existPhone){
                result+="手机号 ";
            }
            if(existIDcard){
                result+="身份证号 ";
            }
        }else {
            result="未检测到敏感信息";
        }
        System.out.println(result);
    }

}
