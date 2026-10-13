class Solution {
    public long maxAlternatingSum(int[] nums) {
        int l = nums.length;
        long[] sq = new long[l];
        for(int i=0;i<l;i++){
            sq[i]=nums[i]*nums[i];
        }
        Arrays.sort(sq);
        long score=0;
        for(int i=0;i<l/2;i++){
            score-=sq[i];
        }
        for(int i=l/2;i<l;i++){
            score+=sq[i];
        }
        return score;
    }
}