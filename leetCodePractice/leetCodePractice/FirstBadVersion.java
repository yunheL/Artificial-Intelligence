package leetCodePractice;

public class FirstBadVersion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//first bad is 6
		System.out.println(firstBadVersion(4));

	}
	
	public static boolean isBadVersion(int ver){
		if (ver<1)
			return false;
		
		return true;
	}
	
	public static int firstBadVersion(int n) {
        
		if(n==1 && isBadVersion(1))
            return 1;
        if(n==2){
            if(!isBadVersion(1) && !isBadVersion(2))
                return -1;
            if(isBadVersion(1))
                return 1;
            else
                return 2;
        }
        
        int curr = n/2;
        int hi = n; int low = 1;
        
        while(curr < n && curr > 0){
            
            if(isBadVersion(curr)){
                if(curr - low < 2)
                {
                	if (isBadVersion(low))
                		return low;
                	else
                		return curr;
                }
                
                hi = curr;

                curr = hi - (hi - low) / 2;
                System.out.println(low);
                
            }
            else{

                
                low = curr;
                curr = low + (hi - low) / 2;
                
                if(hi - curr < 2)
                {
                	if(isBadVersion(curr))
                		return curr;
                	else
                		return hi;
                }

                
                System.out.println(curr);
            }
        }
        return -1;
    }

}
