package com.luo.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 单词接龙问题
 * 1. 两个单词之间是否能转换,依据是这两个单词之间差一个字母
 * 2. 最短路径
 *
 * @author lkx
 */

public class WordSolitaire {
    /**
     * 基于邻接矩阵
     * 基于邻接表
     * 最短路径 迪杰斯特拉
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int endIndex = -1;
        int beginIndex = -1;
        for (int i = 0; i < wordList.size(); i++) {
            if (endWord.equals(wordList.get(i))) {
                endIndex = i;
            }
            if (beginWord.equals(wordList.get(i))) {
                beginIndex = i;
            }
        }
        if (endIndex == -1) {
            return 0;
        }
        if (beginIndex == -1) {
            wordList.add(beginWord);
            beginIndex = wordList.size() - 1;
        }
        // beginWord和endWord是都一定存在于wordList里面
        // 找到从beginIndex到endIndex的最短路径
        int[][] graph = createGraph(wordList);
        return bfs(graph, beginIndex, endIndex);
    }

    /**
     * 时间复杂度 = n * n
     * @param graph 矩阵
     * @param beginIndex 开始
     * @param endIndex 结束
     * @return 返回路径
     */
    private int bfs(int[][] graph, int beginIndex, int endIndex) {
        Queue<Integer> qu = new LinkedList<>();
        // false代表还没入过队,true代表已经入过队
        boolean[] isQue = new boolean[graph.length];
        //队列进行初始化
        qu.offer(beginIndex);
        isQue[beginIndex] = true;
        //代表当前圈有多少节点
        int cnt = 1;
        int layer = 0;
        boolean isFind = false;
        while (!qu.isEmpty()) {
            int nextCnt = 0;
            layer++;
            for (int k = 0; k < cnt; k++) {
                int cur = qu.poll();
                if (cur == endIndex) {
                    isFind = true;
                    break;
                }
                //需要cur的邻居节点入队,代表的是下一圈
                for (int i = 0; i < graph.length; i++) {
                    if (graph[cur][i] == 1 && !isQue[i]) {
                        qu.offer(i);
                        isQue[i] = true;
                        nextCnt++;
                    }
                }
            }
            if (isFind) {
                break;
            }
            cnt = nextCnt;
        }
        if (!isFind) {
            layer = 0;
        }
        return layer;
    }

    /**
     *
     * @param wordList 字典
     * @return 返回矩阵
     */
    private int[][] createGraph(List<String> wordList) {
        int[][] graph = new int[wordList.size()][wordList.size()];
        for (int i = 0; i < wordList.size(); i++) {
            for (int j = 0; j < wordList.size(); j++) {
                // 判断wordList[i] 和wordList[j] 是否存在边
                boolean r = judgeEdge(wordList.get(i), wordList.get(j));
                if (r) {
                    graph[i][j] = graph[j][i] = 1;
                }
            }
        }
        return graph;
    }

    // 两个单词之间差一个字母
    /**
     *
     * @param word1 第一个单词
     * @param word2 第二个单词
     * @return 返回是否相邻
     */
    private boolean judgeEdge(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }
        int diff = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diff++;
            }
        }
        return diff == 1;
    }

}

