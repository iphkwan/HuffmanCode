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
	
	//����������
	public void launch(String file,String decodeFile) throws Exception{
		getSourceCode(file);
		getOriginFile(decodeFile);
	}
	
	//��ȡ�����ļ�������Ԥ����
	private void getSourceCode(String file) throws Exception{
		src = new File(file);
		FileInputStream fr = new FileInputStream(src);
		ObjectInputStream or = new ObjectInputStream(fr);
		BufferedInputStream br = new BufferedInputStream(fr);
		String tmp = new String();
		StringBuffer btmp = new StringBuffer();
		byte[] buf = new byte[1];
		
		//��ȡ���л���HUFFMAN�����и�ԭ��׼������
		root = (HuffmanNode)or.readObject();
		
		//�Ӷ�ȡ����SHORT�������Ӧ��HUFFMAN���봮
		while (br.read(buf) != -1){
			btmp.delete(0,btmp.length());
			tmp = Integer.toBinaryString(buf[0]);
			
			if (tmp.length() > 8)
				tmp = tmp.substring(tmp.length() - 8);
			for(int i = 0; i < 8 - tmp.length(); i++)  
                btmp.append('0');
			
			code.append(btmp + tmp);
		}
		
		//�����������8λ�����ɸ����ϵ��ֽ�
		code.delete(code.length() - 8 - Short.parseShort(code.substring(code.length() - 8),2),
				code.length() - 1);
		
		fr.close();
		or.close();
		br.close();
	}
	
	//���ݵõ���HUFFMAN���봮���н���
	private void getOriginFile(String file) throws Exception{
		
		HuffmanNode p = root;
		BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(file));
		
		//���ݱ��봮������������������ұ��������ڸ���Ҷ�ӵ�·��Ψһ��������Ҷ�Ӽ������
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
