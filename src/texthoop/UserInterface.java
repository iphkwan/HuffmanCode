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
			System.out.println("请选择需要的功能\n1.压缩\n2.解压\n0.退出\n");
			cmd = Integer.parseInt(in.nextLine());
			if (cmd == 1){
				System.out.println(">>输入待压缩文件的存放路径.\n");
				src = in.nextLine();
				System.out.println(">>输入压缩文件的存放路径.\n");
				encode = in.nextLine();
				encoder.launch(src,encode);
				System.out.println("压缩成功,按任意键继续...\n");
				in.nextLine();
			}
			else if (cmd == 2){
				System.out.println(">>输入待解压文件的存放路径.\n");
				encode = in.nextLine();
				System.out.println(">>输入解压文件的存放路径.\n");
				decode = in.nextLine();
				HuffmanDecode decoder = new HuffmanDecode();
				decoder.launch(encode,decode);
				System.out.println("解压成功,按任意键继续...\n");
				in.nextLine();
			}
			else {
				System.exit(0);
			}
		}
	}

}