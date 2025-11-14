package controller;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;


import model.Locacao;
import model.Cliente;
import model.ClienteRepo;
import model.Veiculo;
import model.VeiculoRepo;
import view.TelaDevolverVeiculos;
import view.TelaIncluirVeiculo;
import view.TelaLocarVeiculos;
import view.TelaManterClientes;
import view.TelaVenderVeiculos;

public class Main extends JFrame{
	private static final long serialVersionUID = -9151783484566916823L;
	public static int fh = (int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.56), fw = (int)( Toolkit.getDefaultToolkit().getScreenSize().height * 1.08);
	public static List<Cliente> clientes = ClienteRepo.load();
    public static List<Veiculo> veiculos = VeiculoRepo.load();
 	public static List<Locacao> locacoes;
 	public static VeiculoController veiculosController = new VeiculoController();
	public static ClienteController clienteController = new ClienteController();
	public static LocacaoController locacaoController = new LocacaoController();


	
	private static Container mainc;
	private static JPanel telas, main;
	private static JLabel mainlabel;
	
	//botões para ir para as telas
	private JButton ManterClientes, IncluirVeiculos, LocarVeiculos, DevolverVeiculos, VenderVeiculos;
	
	public static void main(String args[]){
		DatabaseInitializer.initialize();
	    new Main().setVisible(true);
	}
	
	@SuppressWarnings("serial")
	public Main() {
		super("Sistema Empresarial");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(fw, fh);
		BufferedImage logo;
		try {
			logo = ImageIO.read(Main.class.getResource("/logo.png"));
			this.setIconImage(logo);
			mainlabel = new JLabel("") {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.drawImage(logo, (this.getWidth()-this.getHeight())/2, 0, this.getHeight(), this.getHeight(), null);
	            }
	        };
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mainc = getContentPane();
		mainc.setLayout(new BorderLayout());
		
		telas = new JPanel(new GridLayout(1,0,1,1));
		ManterClientes = new JButton("Manter Clientes");
		IncluirVeiculos = new JButton("Incluir Veículos");
		LocarVeiculos = new JButton("Locar Veículos");
		DevolverVeiculos = new JButton("Devolver Veículos");
		VenderVeiculos = new JButton("Vender Veículos");

		telas.add(ManterClientes);
		telas.add(IncluirVeiculos);
		telas.add(LocarVeiculos);
		telas.add(DevolverVeiculos);
		telas.add(VenderVeiculos);
		
		main = new JPanel(new GridLayout(0,1,4,4));
		
		main.add(mainlabel);
		
		mainc.add("North", telas);
		mainc.add("Center", main);
		
		ManterClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.removeAll();
				main.setLayout(new BorderLayout());
				main.add(new TelaManterClientes(), BorderLayout.CENTER);
				main.revalidate();
				main.repaint();
			}
		});
		
		IncluirVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.removeAll();
				main.setLayout(new BorderLayout());
				try {
					main.add(new TelaIncluirVeiculo(veiculosController), BorderLayout.CENTER);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				main.revalidate();
				main.repaint();
			}
		});
		LocarVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocarVeiculos();
				setVisible(true);
			}
		});
		DevolverVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.removeAll();
				main.setLayout(new BorderLayout());
				main.add(new TelaDevolverVeiculos(veiculosController), BorderLayout.CENTER);
				main.revalidate();
				main.repaint();
			}
		});
		VenderVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.removeAll();
				main.setLayout(new BorderLayout());
				main.add(new TelaVenderVeiculos(veiculosController), BorderLayout.CENTER);
				main.revalidate();
				main.repaint();
			}
		});
		float hsb[] = Color.RGBtoHSB(255, 192, 203, null);
		telas.setBackground(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
		main.setBackground(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
	}
	
	public static void LocarVeiculos() {
	    main.removeAll();
	    main.setLayout(new BorderLayout());
	    main.add(new TelaLocarVeiculos(veiculosController), BorderLayout.CENTER);
	    main.revalidate();
	    main.repaint();
	}
	
	public static void DevolverVeiculos() {
		main.removeAll();

		
	}
	public static void VenderVeiculos() {
		main.removeAll();
		
	}
}
