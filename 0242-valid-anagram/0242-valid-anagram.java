class Solution {
    public boolean isAnagram(String s, String t) {
        Map<Character,Integer> hash = new HashMap<>();
        
        if(s.length()!=t.length()){
            return false;
        }

        for(int i=0;i<s.length();i++){
            hash.put(s.charAt(i),hash.getOrDefault(s.charAt(i),0)+1);
        }

        for(int i=0;i<t.length();i++){
            if(!hash.containsKey(t.charAt(i)) || hash.get(t.charAt(i))==0) {
                return false;
            }
            hash.put(t.charAt(i),hash.get(t.charAt(i))-1);
        }
        return true;
    }
}