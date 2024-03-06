package org.zafkiel.frontend;

import org.zafkiel.backend.Lexer;
import org.zafkiel.backend.Tokens;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HomePage extends JFrame {
    public JPanel panelHomePage;
    private JButton botonSalir;
    private JTabbedPane panelArchivos;
    private JButton botonArchivosAbrirCarpeta;
    private JButton botonArchivosAbrirArchivo;
    private JTree tree1;
    private JTextPane textPaneArchivosMostrar;
    private JButton botonArchivosEditarArchivo;
    private JButton botonArchivosGuardarArchivo;
    private JButton botonArchivosNuevoArchivo;
    private JButton botonArchivosEliminarArchivo;
    private JTree tree2;
    private JTextPane textPaneConsultasMostrar;
    private JTextPane textPaneConsultasFinal;
    private JTextPane textPaneConsultasInicio;
    private JButton botonConsultasRealizarInstruccion;
    private JTextPane textPaneReporteLexicoInstruccion;
    private JTextPane textPaneReporteLexicoArchivo;
    private JEditorPane editorPaneReporteSintacticoInstruccion;

    //Variable para guardar la ruta de los Archivos
    private String archivosRutaArchivos;
    private String archivosRutaArchivos2;
    public HomePage() {
        //---   PANEL CARGAR PROYECTO   ---

        //Limpiar el JTree
        DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode());
        tree1.setModel(treeModel);
        //---   PANEL REALIZAR CONSULTAS  ---
        //Limpiar el JTree
        tree2.setModel(treeModel);


        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        botonArchivosAbrirCarpeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                archivosRutaArchivos = "";
                archivosSeleccionarCarpeta();
            }
        });

        tree1.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                archivosRutaArchivos2 = "";
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    TreePath path = new TreePath(selectedNode.getPath());
                    String filePath = "";
                    for (Object pathComponent : path.getPath()) {
                        filePath += File.separator + pathComponent.toString();
                    }
                    archivosRutaArchivos2 = archivosRutaArchivos+filePath;
                    System.out.println(archivosRutaArchivos2);
                    mostrarContenidoArchivo(archivosRutaArchivos2);
                }
            }
        });

        botonConsultasRealizarInstruccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    analizarLexico();
                } catch (IOException ex) {
                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    //   ---   METODOS Y FUNCIONES   ---

    //Llenamos los tree

    private void archivosSeleccionarCarpeta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File carpetaSeleccionada = fileChooser.getSelectedFile();
            archivosRutaArchivos = carpetaSeleccionada.getAbsolutePath();
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(carpetaSeleccionada.getName());
            archivosLlenarArbol(carpetaSeleccionada, root);
            DefaultTreeModel treeModel = new DefaultTreeModel(root);
            tree1.setModel(treeModel);
            tree2.setModel(treeModel);
            System.out.println(archivosRutaArchivos);
        }
    }

    private void archivosLlenarArbol(File carpeta, DefaultMutableTreeNode nodoPadre) {
        File[] archivos = carpeta.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(archivo.getName());
                    nodoPadre.add(nuevoNodo);
                    archivosLlenarArbol(archivo, nuevoNodo);
                } else {
                    String nombreArchivo = archivo.getName();
                    if (nombreArchivo.endsWith(".csv") || nombreArchivo.endsWith(".ide")) {
                        DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(nombreArchivo);
                        nodoPadre.add(nuevoNodo);
                    }
                }
            }
        }
    }
    private void mostrarContenidoArchivo(String filePath) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(filePath)));
            textPaneArchivosMostrar.setText(contenido);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void analizarLexico() throws IOException{
        int cont = 1;

        String expr = (String) textPaneConsultasInicio.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        String resultado = "LINEA " + cont + "\t\tSIMBOLO\n";
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                textPaneReporteLexicoInstruccion.setText(resultado);
                return;
            }
            switch (token) {
                case Linea:
                    cont++;
                    resultado += "LINEA " + cont + "\n";
                    break;
                case Comillas:
                    resultado += "  <Comillas>\t\t" + lexer.lexeme + "\n";
                    break;
                case Cadena:
                    resultado += "  <Tipo de dato>\t" + lexer.lexeme + "\n";
                    break;
                case T_dato:
                    resultado += "  <Tipo de dato>\t" + lexer.lexeme + "\n";
                    break;
                case SELECCIONAR:
                    resultado += "  <Reservada SELECCIONAR>\t" + lexer.lexeme + "\n";
                    break;
                case FILTRAR:
                    resultado += "  <Reservada FILTRAR>\t" + lexer.lexeme + "\n";
                    break;
                case INSERTAR:
                    resultado += "  <Reservada INSERTAR>\t" + lexer.lexeme + "\n";
                    break;
                case ACTUALIZAR:
                    resultado += "  <Reservada ACTUALIZAR>\t" + lexer.lexeme + "\n";
                    break;
                case ASIGNAR:
                    resultado += "  <Reservada ASIGNAR>\t" + lexer.lexeme + "\n";
                    break;
                case ELIMINAR:
                    resultado += "  <Reservada ELIMINAR>\t" + lexer.lexeme + "\n";
                    break;
                case EN:
                    resultado += "  <Reservada EN>\t" + lexer.lexeme + "\n";
                    break;
                case Igual:
                    resultado += "  <Operador igual>\t" + lexer.lexeme + "\n";
                    break;
                case Suma:
                    resultado += "  <Operador suma>\t" + lexer.lexeme + "\n";
                    break;
                case Resta:
                    resultado += "  <Operador resta>\t" + lexer.lexeme + "\n";
                    break;
                case Multiplicacion:
                    resultado += "  <Operador multiplicacion>\t" + lexer.lexeme + "\n";
                    break;
                case Division:
                    resultado += "  <Operador division>\t" + lexer.lexeme + "\n";
                    break;
                case Op_logico:
                    resultado += "  <Operador logico>\t" + lexer.lexeme + "\n";
                    break;
                case Op_incremento:
                    resultado += "  <Operador incremento>\t" + lexer.lexeme + "\n";
                    break;
                case Op_relacional:
                    resultado += "  <Operador relacional>\t" + lexer.lexeme + "\n";
                    break;
                case Op_atribucion:
                    resultado += "  <Operador atribucion>\t" + lexer.lexeme + "\n";
                    break;
                case Parentesis_a:
                    resultado += "  <Parentesis de apertura>\t" + lexer.lexeme + "\n";
                    break;
                case Parentesis_c:
                    resultado += "  <Parentesis de cierre>\t" + lexer.lexeme + "\n";
                    break;
                case Llave_a:
                    resultado += "  <Llave de apertura>\t" + lexer.lexeme + "\n";
                    break;
                case Llave_c:
                    resultado += "  <Llave de cierre>\t" + lexer.lexeme + "\n";
                    break;
                case Corchete_a:
                    resultado += "  <Corchete de apertura>\t" + lexer.lexeme + "\n";
                    break;
                case MenorQue:
                    resultado += "  <Menor Que>\t" + lexer.lexeme + "\n";
                    break;
                case MayorQue:
                    resultado += "  <Mayor Que>\t" + lexer.lexeme + "\n";
                    break;
                case Corchete_c:
                    resultado += "  <Corchete de cierre>\t" + lexer.lexeme + "\n";
                    break;
                case P_coma:
                    resultado += "  <Punto y coma>\t" + lexer.lexeme + "\n";
                    break;
                case Identificador:
                    resultado += "  <Identificador>\t\t" + lexer.lexeme + "\n";
                    break;
                case Numero:
                    resultado += "  <Numero>\t\t" + lexer.lexeme + "\n";
                    break;
                case ERROR:
                    resultado += "  <Simbolo no definido>\n";
                    break;
                default:
                    resultado += "  < " + lexer.lexeme + " >\n";
                    break;
            }
        }
    }
}
