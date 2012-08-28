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
	
	//启动解码器
	public void launch(String file,String codeFile) throws Exception{
		
		getSrc(file);
		buildTree();
		getHuffmanCode(root);
		saveEncode(codeFile);
		
	}
	
	//根据读入文件时构建的优先队列建立HUFFMAN树
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
		//如果只有一个节点，则该节点的HUFFMAN编码为0
		if (root.isLeaf())
			root.huffCode = new StringBuffer('0');
		else root.huffCode = new StringBuffer();
		
		//释放优先队列占用的空间
		queue = null;
		System.gc();
		
	}
	
	//为HUFFMAN树的节点计算HUFFMAN编码
	private void getHuffmanCode(HuffmanNode node){
		
		if (node == null)
			return;
		else if (!node.isLeaf()){
			node.left.huffCode = new StringBuffer(node.huffCode);
			node.right.huffCode = new StringBuffer(node.huffCode);
			
			//左子树的HUFFMAN为父亲的HUFFMAN+'0'，右子树同理
			node.left.huffCode.append('0');
			node.right.huffCode.append('1');
			getHuffmanCode(node.left);
			getHuffmanCode(node.right);
		}
		else {
			//对应实际数据的叶子节点放入MAP以便查找HUFFMAN编码
			char_code_map.put(node.ch,node.huffCode);
		}
		
	}
	
	//读取源文件并进行预处理
	private void getSrc(String file) throws Exception{
		
		int ch;
		char key;
		src = new File(file);
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
		
		//统计每个字符的出现频率
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
		
		//将所有节点放入优先队列等待处理
		for (Iterator<Map.Entry<Character,HuffmanNode>> iter = char_node_map.entrySet().iterator();
			iter.hasNext();){
			Map.Entry<Character,HuffmanNode> entry = iter.next();
            HuffmanNode node = entry.getValue();
			queue.add(node);
		}
		
	}
	
	//进行编码并保存
	private void saveEncode(String file) throws Exception{
		int ch;
		long srcLen,codeLen;
		short len;
		char key;
		
		len = 0;
		codeLen = 0;
		srcLen = src.length();
		System.out.println("源文件大小为" + srcLen + "字节\n");
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(src));
		DataOutputStream fw = new DataOutputStream(
			new BufferedOutputStream(new FileOutputStream(file)));
		ObjectOutputStream ow = new ObjectOutputStream(fw);
		
		//将HUFFMAN树序列化后写入编码文件
		ow.writeObject(root);
		StringBuilder ob = new StringBuilder(8 + 255);
		
		//每8位压缩成一个SHORT，写入编码文件
		while ((ch = input.read()) != -1){
			key = (char)ch;
			ob.append(char_code_map.get(key).toString());
			
			while (ob.length() > 8){
				fw.write(Short.parseShort(ob.substring(0,8),2));
				ob.delete(0,8);
				codeLen++;
			}
		}
		
		//剩余文件不足8BIT时在后面补0，并用LEN记录补0的个数
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
		
		System.out.println("压缩文件大小为" + codeLen + "字节\n");
		System.out.println("压缩比为" + ((double)codeLen / (double)srcLen) + "%\n");
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
