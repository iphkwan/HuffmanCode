package texthoop;

import java.util.*;

public class UserInterface {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HuffmanEncode encoder = new HuffmanEncode();
		String src = new String();
		String encode = new String();
		String decode = new String();
		Scanner in = new Scanner(System.in);
		int cmd;
		while (true){
			System.out.println("��ѡ����Ҫ�Ĺ���\n1.ѹ��\n2.��ѹ\n0.�˳�\n");
			cmd = Integer.parseInt(in.nextLine());
			if (cmd == 1){
				System.out.println(">>�����ѹ���ļ��Ĵ��·��.\n");
				src = in.nextLine();
				System.out.println(">>����ѹ���ļ��Ĵ��·��.\n");
				encode = in.nextLine();
				encoder.launch(src,encode);
				System.out.println("ѹ���ɹ�,�����������...\n");
				in.nextLine();
			}
			else if (cmd == 2){
				System.out.println(">>�������ѹ�ļ��Ĵ��·��.\n");
				encode = in.nextLine();
				System.out.println(">>�����ѹ�ļ��Ĵ��·��.\n");
				decode = in.nextLine();
				HuffmanDecode decoder = new HuffmanDecode();
				decoder.launch(encode,decode);
				System.out.println("��ѹ�ɹ�,�����������...\n");
				in.nextLine();
			}
			else {
				System.exit(0);
			}
		}
	}

}