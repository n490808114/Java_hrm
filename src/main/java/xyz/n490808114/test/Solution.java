package xyz.n490808114.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solution {
    public static void main(String[] args){
        int[] nums= new int[]{-10,-3,0,5,9};
        TreeNode node = new Solution().sortedArrayToBST(nums);
        List<List<Integer>> list = new Solution().levelOrderBottom(node);
        for(List<Integer> list1 :list){
            for(Integer a:list1){
                System.out.print(a+ " ");
            }
            System.out.print("\n");
        }
    }
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for(int a=nums1.length-1;a>=0;a--){
            if(n>0 && m >0){
                if(nums1[m-1] >= nums2[n-1]){
                    nums1[a] = nums1[m-1];
                    m--;
                }else{
                    nums1[a] = nums2[n-1];
                    n--;
                }
            }else if(m<=0){
                nums1[a] = nums2[n-1];
                n--;
            }
        }
    }
    public ListNode deleteDuplicates(ListNode head) {
        ListNode nextNode = head.next;
        ListNode lastNode = head;
        while(lastNode.next != null){
            if (nextNode.val == lastNode.val){
                lastNode.next = nextNode.next;
            }else{
                lastNode = lastNode.next;
            }
            nextNode = nextNode.next;
        }
        return head;
    }
    public TreeNode sortedArrayToBST(int[] nums) {
        TreeNode node = new TreeNode(0);
        setNode(node,-1,nums.length,nums);
        return node;
    }
    public void setNode(TreeNode node,int min,int max,int[] nums){
        int mid = (int) (min + (max-min)/2);
        if(mid < max && mid > min){
            node = new TreeNode(nums[mid]);
            if((mid-min)>1){
                node.left = new TreeNode(0);
                setNode(node.left,min,mid,nums);
            }
            if((max-mid)>1){
                node.right = new TreeNode(0);
                setNode(node.right,mid,min,nums);
            }
        }
    }
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        add(root,0,list);
        return list;
    }
    public void add(TreeNode a,int floor,List<List<Integer>> list){
        if(a != null){
            if(floor>=list.size()){
                list.add(0,new LinkedList<Integer>());
            }
            List<Integer> alist  = list.get(list.size()-1-floor);
            alist.add(a.val);
            floor++;
            add(a.left,floor,list);
            add(a.right,floor,list);
        }

    }
}
class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x){val = x;}
}
class ListNode{
    int val;
    ListNode next;
    ListNode(int x){val=x;}
}
