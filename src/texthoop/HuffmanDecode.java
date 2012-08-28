package texthoop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;

public class HuffmanDecode {
	
	HuffmanDecode() throws Exception{
		code = new StringBuffer();
	}
	
	//启动解码器
	public void launch(String file,String decodeFile) throws Exception{
		getSourceCode(file);
		getOriginFile(decodeFile);
	}
	
	//读取编码文件并进行预处理
	private void getSourceCode(String file) throws Exception{
		src = new File(file);
		FileInputStream fr = new FileInputStream(src);
		ObjectInputStream or = new ObjectInputStream(fr);
		BufferedInputStream br = new BufferedInputStream(fr);
		String tmp = new String();
		StringBuffer btmp = new StringBuffer();
		byte[] buf = new byte[1];
		
		//读取序列化的HUFFMAN树进行复原，准备解码
		root = (HuffmanNode)or.readObject();
		
		//从读取到的SHORT中译出对应的HUFFMAN编码串
		while (br.read(buf) != -1){
			btmp.delete(0,btmp.length());
			tmp = Integer.toBinaryString(buf[0]);
			
			if (tmp.length() > 8)
				tmp = tmp.substring(tmp.length() - 8);
			for(int i = 0; i < 8 - tmp.length(); i++)  
                btmp.append('0');
			
			code.append(btmp + tmp);
		}
		
		//处理最后因不足8位而若干个补上的字节
		code.delete(code.length() - 8 - Short.parseShort(code.substring(code.length() - 8),2),
				code.length() - 1);
		
		fr.close();
		or.close();
		br.close();
	}
	
	//根据得到的HUFFMAN编码串进行解码
	private void getOriginFile(String file) throws Exception{
		
		HuffmanNode p = root;
		BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(file));
		
		//根据编码串决定向左遍历还是向右遍历，由于根到叶子的路径唯一，遍历到叶子即可输出
		for (int i = 0;i < code.length();i++){
			if (code.charAt(i) == '0'){
				p = p.left;
			}
			else p = p.right;
			if (p.isLeaf()){
				bw.write(p.ch);
				p = root;
			}
			if (i > 10000){
				code.delete(0,i);
				i = 0;
			}
		}
		
		bw.close();
		
	}
	
	private File src;
	private HuffmanNode root;
	private StringBuffer code;
	
}
