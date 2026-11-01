
class Solution {

    int count = 0;
    int lower, upper;

    public int countRangeSum(int[] nums, int lower, int upper) {

        this.lower = lower;
        this.upper = upper;

        long[] prefix = new long[nums.length + 1];

        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        mergeSort(prefix, 0, prefix.length);

        return count;
    }

    void mergeSort(long[] a, int left, int right) {

        if (right - left <= 1) return;

        int mid = left + (right - left) / 2;

        mergeSort(a, left, mid);
        mergeSort(a, mid, right);

        int j = mid;
        int k = mid;

        for (int i = left; i < mid; i++) {

            while (j < right && a[j] - a[i] < lower) {
                j++;
            }

            while (k < right && a[k] - a[i] <= upper) {
                k++;
            }

            count += k - j;
        }

        long[] temp = new long[right - left];

        int i = left;
        j = mid;
        int t = 0;

        while (i < mid && j < right) {
            if (a[i] <= a[j]) {
                temp[t++] = a[i++];
            } else {
                temp[t++] = a[j++];
            }
        }

        while (i < mid) {
            temp[t++] = a[i++];
        }

        while (j < right) {
            temp[t++] = a[j++];
        }

        for (int p = 0; p < temp.length; p++) {
            a[left + p] = temp[p];
        }
    }
}
