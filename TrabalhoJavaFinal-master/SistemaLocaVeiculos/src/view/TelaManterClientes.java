
package view;

import controller.ClienteController;
import controller.Main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;
import model.Cliente;

public class TelaManterClientes extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtNome;
    private JTextField txtSobrenome;
    private JTextField txtEndereco;
    private JTable table;
	ClienteController clienteController = new ClienteController();

	
	private static boolean empty(JTextComponent comp) {
	    String s = comp.getText().replaceAll("[ _.-]", "").trim();
	    return s.isEmpty();
	}
	
	private static String clear(JTextComponent comp) {
	    String s = comp.getText().replaceAll("[ _.-]", "").trim();
	    return s;
	}
	
	private static boolean validarCPF(String cpf) {
	    cpf = cpf.replaceAll("[^0-9]", "");

	    if (cpf.length() != 11)
	        return false;

	    if (cpf.matches("(\\d)\\1{10}"))
	        return false;

	    try {
	        int[] pesos1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
	        int soma = 0;
	        for (int i = 0; i < 9; i++)
	            soma += (cpf.charAt(i) - '0') * pesos1[i];
	        int digito1 = 11 - (soma % 11);
	        if (digito1 >= 10)
	            digito1 = 0;

	        int[] pesos2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	        soma = 0;
	        for (int i = 0; i < 10; i++)
	            soma += (cpf.charAt(i) - '0') * pesos2[i];
	        int digito2 = 11 - (soma % 11);
	        if (digito2 >= 10)
	            digito2 = 0;

	        return digito1 == (cpf.charAt(9) - '0') && digito2 == (cpf.charAt(10) - '0');
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	public static boolean cpfExiste(String CPF) {
        for (Cliente c : Main.clientes) {
            if (c.getCPF().equalsIgnoreCase(CPF)) {
                return true;
            }
        }
        return false;
    }

    public TelaManterClientes() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);

        JLabel titulo = new JLabel("CADASTRO DE CLIENTES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titulo, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel();
        GridBagLayout gbl_formPanel = new GridBagLayout();
        gbl_formPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_formPanel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_formPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_formPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        formPanel.setLayout(gbl_formPanel);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_nomeLabel = new GridBagConstraints();
        gbc_nomeLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nomeLabel.anchor = GridBagConstraints.WEST;
        gbc_nomeLabel.gridx = 0;
        gbc_nomeLabel.gridy = 0;
        formPanel.add(nomeLabel, gbc_nomeLabel);

        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_txtNome = new GridBagConstraints();
        gbc_txtNome.insets = new Insets(0, 0, 5, 5);
        gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNome.weightx = 1.0;
        gbc_txtNome.gridx = 1;
        gbc_txtNome.gridy = 0;
        formPanel.add(txtNome, gbc_txtNome);

        JLabel sobrenomeLabel = new JLabel("Sobrenome:");
        sobrenomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_sobrenomeLabel = new GridBagConstraints();
        gbc_sobrenomeLabel.anchor = GridBagConstraints.WEST;
        gbc_sobrenomeLabel.insets = new Insets(0, 20, 5, 5);
        gbc_sobrenomeLabel.gridx = 2;
        gbc_sobrenomeLabel.gridy = 0;
        formPanel.add(sobrenomeLabel, gbc_sobrenomeLabel);

        txtSobrenome = new JTextField();
        txtSobrenome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_txtSobrenome = new GridBagConstraints();
        gbc_txtSobrenome.insets = new Insets(0, 0, 5, 0);
        gbc_txtSobrenome.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtSobrenome.weightx = 1.0;
        gbc_txtSobrenome.gridx = 3;
        gbc_txtSobrenome.gridy = 0;
        formPanel.add(txtSobrenome, gbc_txtSobrenome);

        JLabel enderecoLabel = new JLabel("Endereço:");
        enderecoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_enderecoLabel = new GridBagConstraints();
        gbc_enderecoLabel.anchor = GridBagConstraints.WEST;
        gbc_enderecoLabel.insets = new Insets(0, 0, 5, 5);
        gbc_enderecoLabel.gridx = 0;
        gbc_enderecoLabel.gridy = 1;
        formPanel.add(enderecoLabel, gbc_enderecoLabel);

        txtEndereco = new JTextField();
        txtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_txtEndereco = new GridBagConstraints();
        gbc_txtEndereco.insets = new Insets(0, 0, 5, 0);
        gbc_txtEndereco.gridwidth = 3;
        gbc_txtEndereco.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtEndereco.weightx = 1.0;
        gbc_txtEndereco.gridx = 1;
        gbc_txtEndereco.gridy = 1;
        formPanel.add(txtEndereco, gbc_txtEndereco);

        JLabel rgLabel = new JLabel("RG:");
        rgLabel.setHorizontalAlignment(SwingConstants.LEFT);
        rgLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_rgLabel = new GridBagConstraints();
        gbc_rgLabel.anchor = GridBagConstraints.WEST;
        gbc_rgLabel.insets = new Insets(0, 0, 0, 5);
        gbc_rgLabel.gridx = 0;
        gbc_rgLabel.gridy = 2;
        formPanel.add(rgLabel, gbc_rgLabel);

        MaskFormatter rgMask = null;
        try {
            rgMask = new MaskFormatter("AA.AAA.AAA-A");
            rgMask.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JFormattedTextField txtRg = new JFormattedTextField(rgMask);
        txtRg.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_txtRg = new GridBagConstraints();
        gbc_txtRg.insets = new Insets(0, 0, 0, 5);
        gbc_txtRg.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtRg.weightx = 1.0;
        gbc_txtRg.gridx = 1;
        gbc_txtRg.gridy = 2;
        formPanel.add(txtRg, gbc_txtRg);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_cpfLabel = new GridBagConstraints();
        gbc_cpfLabel.anchor = GridBagConstraints.EAST;
        gbc_cpfLabel.insets = new Insets(0, 20, 0, 5);
        gbc_cpfLabel.gridx = 2;
        gbc_cpfLabel.gridy = 2;
        formPanel.add(cpfLabel, gbc_cpfLabel);

        MaskFormatter cpfMask = null;
        try {
            cpfMask = new MaskFormatter("AAA.AAA.AAA-AA");
            cpfMask.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JFormattedTextField txtCpf = new JFormattedTextField(cpfMask);
        txtCpf.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_txtCpf = new GridBagConstraints();
        gbc_txtCpf.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCpf.weightx = 1.0;
        gbc_txtCpf.gridx = 3;
        gbc_txtCpf.gridy = 2;
        formPanel.add(txtCpf, gbc_txtCpf);
        
        topPanel.add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JButton btnAdicionar = new JButton("Adicionar\r\n");
        btnAdicionar.setVerticalAlignment(SwingConstants.TOP);
        btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAdicionar.setAlignmentX(0.5f);
        buttonsPanel.add(btnAdicionar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setVerticalAlignment(SwingConstants.TOP);
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCancelar.setAlignmentX(0.5f);
        buttonsPanel.add(btnCancelar);
        btnCancelar.setVisible(false);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setVerticalAlignment(SwingConstants.TOP);
        btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSalvar.setAlignmentX(0.5f);
        buttonsPanel.add(btnSalvar);
        btnSalvar.setVisible(false);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setVerticalAlignment(SwingConstants.TOP);
        btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnExcluir.setAlignmentX(0.5f);
        buttonsPanel.add(btnExcluir);
        btnExcluir.setVisible(false);
        
        topPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        ClienteTableModel modelo = new ClienteTableModel(Main.clientes);
	    table = new JTable(modelo);
	    JScrollPane scroll = new JScrollPane(table);
	    scroll.setPreferredSize(new Dimension(400, 200));
	    add(scroll, BorderLayout.CENTER);
	    
		btnAdicionar.addActionListener(e -> {
			if (empty(txtNome) || empty(txtSobrenome) || empty(txtEndereco) || empty(txtRg) || empty(txtCpf)) {
				JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de adicionar.",
					"Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
				return;
			}

			String cpfcheck = clear(txtCpf);
			if (!validarCPF(cpfcheck)) {
				JOptionPane.showMessageDialog(this, "CPF inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (clienteController.cpfExiste(cpfcheck)) {
				JOptionPane.showMessageDialog(this, "CPF já cadastrado.");
				return;
			}

			Cliente novo = new Cliente(
				txtNome.getText().trim(),
				txtSobrenome.getText().trim(),
				clear(txtRg),
				clear(txtCpf),
				txtEndereco.getText().trim()
			);

			clienteController.adicionarCliente(novo, modelo);
			txtNome.setText("");
			txtSobrenome.setText("");
			txtRg.setText("");
			txtCpf.setText("");
			txtEndereco.setText("");
		});

	    
	    btnCancelar.addActionListener(e -> {
	    	table.clearSelection();
	    });

		btnSalvar.addActionListener(e -> {
			int i = table.getSelectedRow();
			if (i >= 0) {
				Cliente c = Main.clientes.get(i);
				c.setNome(txtNome.getText().trim());
				c.setSobrenome(txtSobrenome.getText().trim());
				c.setRG(clear(txtRg));
				c.setCPF(clear(txtCpf));
				c.setEndereco(txtEndereco.getText().trim());
				clienteController.atualizarCliente(c, modelo);
			} else {
				JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela.");
			}
		});


		btnExcluir.addActionListener(e -> {
			int i = table.getSelectedRow();
			if (i >= 0) {
				Cliente c = Main.clientes.get(i);
				boolean possuiLocacao = Main.veiculos.stream()
					.anyMatch(v -> v.getLocacao() != null && v.getLocacao().getCliente().equals(c));

				if (possuiLocacao) {
					JOptionPane.showMessageDialog(this, "Cliente possui veículos locados e não pode ser excluído.");
				} else {
					clienteController.removerCliente(c, modelo);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela.");
			}
		});


	    table.getSelectionModel().addListSelectionListener(e -> {
	        int i = table.getSelectedRow();
	        if (i >= 0) {
	            Cliente c = Main.clientes.get(i);
	            txtNome.setText(c.getNome());
	            txtSobrenome.setText(c.getSobrenome());
	            txtRg.setText(c.getRG());
	            txtCpf.setText(c.getCPF());
	            txtEndereco.setText(c.getEndereco());
	            btnAdicionar.setVisible(false);
	            btnCancelar.setVisible(true);
	            btnSalvar.setVisible(true);
	            btnExcluir.setVisible(true);
	        }
	        else {
	        	txtNome.setText("");
	            txtSobrenome.setText("");
	            txtRg.setText("");
	            txtCpf.setText("");
	            txtEndereco.setText("");
	            btnAdicionar.setVisible(true);
	            btnCancelar.setVisible(false);
	            btnSalvar.setVisible(false);
	            btnExcluir.setVisible(false);
	        }
	    });
    }
}
