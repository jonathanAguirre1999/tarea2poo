package ups.poo.igu;
import ups.poo.logica.*;

import java.awt.EventQueue;

public class MainAudioVisual {
	public static void main(String[] args) {
		GestorContenidos gestor = new GestorContenidos();

		
			EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				MenuPrincipal menuPrin = new MenuPrincipal(gestor);
				menuPrin.setVisible(true);
				menuPrin.setLocationRelativeTo(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}						
		});
		
    }
}