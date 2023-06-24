package org.guuproject.application;

class Solution {
    public int strStr(String haystack, String needle) {
        int position = 0;
        for (int i=0;i<haystack.length();i++){
            if (haystack.charAt(i)==needle.charAt(position)){
                position+=1;
                if (position==needle.length()) return i+1-position;
            }else position=0;
        }
        return -1;
    }
}