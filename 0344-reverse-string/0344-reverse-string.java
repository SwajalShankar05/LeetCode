class Solution {
    public void reverseString(char[] s) {
        int size = s.length;
        int l=0;
        int r=size-1;
        char[] res = new char[size];
        for(int i=0;i<size/2;i++){
            char temp=s[l];
            s[l]=s[r];
            s[r]=temp;
            l++;
            r--;
        }        
    }
}