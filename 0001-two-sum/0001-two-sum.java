class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> res = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int a = nums[i];
            int req = target-a;
            if(res.containsKey(req)){
                return new int[]{res.get(req), i};
            }
            res.put(nums[i],i);
        }
        return new int[]{};
    }
}