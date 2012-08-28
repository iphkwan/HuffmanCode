package texthoop;

import java.io.Serializable;

public class HuffmanNode implements Comparable<HuffmanNode>,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3822725337479220173L;//用于对象序列化以存储HUFFMAN树

	char ch;//当前节点存储的字符
	
	StringBuffer huffCode;//当前节点的字符对应的HUFFMAN编码
	
	HuffmanNode parent;
	
	HuffmanNode left;
	
	HuffmanNode right;
	
	int freq;//当前节点字符出现的频率
	
	//用于优先队列排序
	public int compareTo(HuffmanNode n) {
		return this.freq - n.freq;
	}
	
	public HuffmanNode(){
		ch = 0xfffe;
	}
	
	public HuffmanNode(char c,int f){
		ch = c;
		freq = f;
	}
	
	public boolean isLeaf(){
		return left == null && right == null;
	}
	
}
