class Solution {
    public String removeOuterParentheses(String s) {
        String res = "";
        int open=0;
        int n=s.length();
        for(int i=0;i<n;i++){
            if(s.charAt(i)=='('){
                if(open>0){
                    res+=s.charAt(i);
                }
                open++;
            }else{
                open--;
                if(open>0){
                    res+=s.charAt(i);
                }
            }
        }
        return res;
    }
}