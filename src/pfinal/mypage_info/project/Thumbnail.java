package pfinal.mypage_info.project;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

public class Thumbnail {
	public Thumbnail() {}
	public static void createImage(String loadFile, String saveFile, int zoom)
			throws IOException {
		File save = new File(saveFile); // ����� �̹��� ����
		RenderedOp rOp = JAI.create("fileload", loadFile); // �����̹������Ϸ� ��ü����
		BufferedImage im = rOp.getAsBufferedImage(); // ���۵��̹��� ��ü�� ����
		if (zoom <= 0)
			zoom = 1;
		int width = 400;
		int height = 289;
		// �޸𸮿� �̹��� ������ ����
		BufferedImage thumb = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = thumb.createGraphics(); // ���۵��̹�����ü�� ���� �׷���2D��ü�� ����
		g2.drawImage(im, 0, 0, width, height, null); // �޸��� �̹��� ������ ���� �̹�����
														// ������ũ��� �׸���.
		ImageIO.write(thumb, "jpg", save); // �׸� �̹����� ���Ϸ� �����Ѵ�.
	}
}