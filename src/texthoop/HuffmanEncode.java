package texthoop;

import java.util.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class HuffmanEncode {
	
	HuffmanEncode(){
		
		char_node_map = new HashMap<Character,HuffmanNode>();
		char_code_map = new TreeMap<Character,StringBuffer>();
		queue = new PriorityQueue<HuffmanNode>();
		
	}
	
	//����������
	public void launch(String file,String codeFile) throws Exception{
		
		getSrc(file);
		buildTree();
		getHuffmanCode(root);
		saveEncode(codeFile);
		
	}
	
	//���ݶ����ļ�ʱ���������ȶ��н���HUFFMAN��
	private void buildTree(){
		
		while (queue.size() > 1){
			HuffmanNode node1 = queue.poll();
			HuffmanNode node2 = queue.poll();
			node1.huffCode = new StringBuffer();
			node2.huffCode = new StringBuffer();
			HuffmanNode mergeNode = new HuffmanNode();
			
			mergeNode.freq = node1.freq + node2.freq;
			mergeNode.left = (node1.compareTo(node2) > 0)? node1:node2;
			mergeNode.right = (node1.compareTo(node2) > 0)? node2:node1;
			node1.parent = mergeNode;
			node2.parent = mergeNode;
			
			queue.add(mergeNode);
		}
		
		root = queue.poll();
		//���ֻ��һ���ڵ㣬��ýڵ��HUFFMAN����Ϊ0
		if (root.isLeaf())
			root.huffCode = new StringBuffer('0');
		else root.huffCode = new StringBuffer();
		
		//�ͷ����ȶ���ռ�õĿռ�
		queue = null;
		System.gc();
		
	}
	
	//ΪHUFFMAN���Ľڵ����HUFFMAN����
	private void getHuffmanCode(HuffmanNode node){
		
		if (node == null)
			return;
		else if (!node.isLeaf()){
			node.left.huffCode = new StringBuffer(node.huffCode);
			node.right.huffCode = new StringBuffer(node.huffCode);
			
			//��������HUFFMANΪ���׵�HUFFMAN+'0'��������ͬ��
			node.left.huffCode.append('0');
			node.right.huffCode.append('1');
			getHuffmanCode(node.left);
			getHuffmanCode(node.right);
		}
		else {
			//��Ӧʵ�����ݵ�Ҷ�ӽڵ����MAP�Ա����HUFFMAN����
			char_code_map.put(node.ch,node.huffCode);
		}
		
	}
	
	//��ȡԴ�ļ�������Ԥ����
	private void getSrc(String file) throws Exception{
		
		int ch;
		char key;
		src = new File(file);
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
		
		//ͳ��ÿ���ַ��ĳ���Ƶ��
		while ((ch = input.read()) != -1){
			key = (char)ch;			
			if (char_node_map.containsKey(key)){
				(char_node_map.get(key)).freq++;
			}
			else {
				HuffmanNode node = new HuffmanNode(key,1);
				char_node_map.put(node.ch, node);
			}
		}
		input.close();
		
		//�����нڵ�������ȶ��еȴ�����
		for (Iterator<Map.Entry<Character,HuffmanNode>> iter = char_node_map.entrySet().iterator();
			iter.hasNext();){
			Map.Entry<Character,HuffmanNode> entry = iter.next();
            HuffmanNode node = entry.getValue();
			queue.add(node);
		}
		
	}
	
	//���б��벢����
	private void saveEncode(String file) throws Exception{
		int ch;
		long srcLen,codeLen;
		short len;
		char key;
		
		len = 0;
		codeLen = 0;
		srcLen = src.length();
		System.out.println("Դ�ļ���СΪ" + srcLen + "�ֽ�\n");
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(src));
		DataOutputStream fw = new DataOutputStream(
			new BufferedOutputStream(new FileOutputStream(file)));
		ObjectOutputStream ow = new ObjectOutputStream(fw);
		
		//��HUFFMAN�����л���д������ļ�
		ow.writeObject(root);
		StringBuilder ob = new StringBuilder(8 + 255);
		
		//ÿ8λѹ����һ��SHORT��д������ļ�
		while ((ch = input.read()) != -1){
			key = (char)ch;
			ob.append(char_code_map.get(key).toString());
			
			while (ob.length() > 8){
				fw.write(Short.parseShort(ob.substring(0,8),2));
				ob.delete(0,8);
				codeLen++;
			}
		}
		
		//ʣ���ļ�����8BITʱ�ں��油0������LEN��¼��0�ĸ���
		if (ob.length() > 0){
			while (ob.length() <8){
				ob.append('0');
				len++;
			}
			codeLen++;
			fw.write(Short.parseShort(ob.toString(),2));
		}
		fw.write(len);
		codeLen++;
		
		System.out.println("ѹ���ļ���СΪ" + codeLen + "�ֽ�\n");
		System.out.println("ѹ����Ϊ" + ((double)codeLen / (double)srcLen) + "%\n");
		input.close();
		fw.close();
		ow.close();
		
	}
	
	private File src;
	private PriorityQueue<HuffmanNode> queue;
	private Map<Character,HuffmanNode> char_node_map;
	private TreeMap<Character,StringBuffer> char_code_map;
	private HuffmanNode root;
	
}
