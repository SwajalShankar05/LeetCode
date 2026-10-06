class Solution {
    public int minAddToMakeValid(String s) {
        int n = s.length();
        Deque<Character> stack = new ArrayDeque<>();
        int count=0;
        int open=0;
        for(int i=0;i<n;i++){
            if(s.charAt(i)=='('){
                stack.push(s.charAt(i));
                open++;
            }else if(s.charAt(i)==')' && open>0){
                stack.pop();
                open--;
            }else{
                count++;
            }
        }
        return count+open;
    }
}