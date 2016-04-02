package Parser;
/* @@author A0121535R
* Parser for getting an input number
*/
public class InputNumParser {
	
	private static final String REGEX_SPACE = "\\s";
	
	public static int getInputNum(String inputArgs) {
		String[] inputTokens = inputArgs.split(REGEX_SPACE);
		String inputNum=inputTokens[0];
		int num;
		try{
			num=Integer.parseInt(inputNum);
		}
		catch(Exception e){
			num=0;
		}
		if(num<1){
			num=0;
		}
		return num;
	}
}
