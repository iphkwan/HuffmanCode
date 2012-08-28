package texthoop;

import java.io.Serializable;

public class HuffmanNode implements Comparable<HuffmanNode>,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3822725337479220173L;//���ڶ������л��Դ洢HUFFMAN��

	char ch;//��ǰ�ڵ�洢���ַ�
	
	StringBuffer huffCode;//��ǰ�ڵ���ַ���Ӧ��HUFFMAN����
	
	HuffmanNode parent;
	
	HuffmanNode left;
	
	HuffmanNode right;
	
	int freq;//��ǰ�ڵ��ַ����ֵ�Ƶ��
	
	//�������ȶ�������
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
