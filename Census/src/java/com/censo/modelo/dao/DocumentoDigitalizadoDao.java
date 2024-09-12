package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class DocumentoDigitalizadoDao {

    public int adicionarDocumentoDigitalizado(Connection conex, CenDocumentosDigitalizado cendocumentosdigitalizado) {

        String sql = "INSERT INTO CEN_DOCUMENTOS_DIGITALIZADOS (DDIG_NOMBRE,DDIG_RUTA,DDIG_TIPO,"
                + "DDIG_REFERENCIA,DDIG_OBSERVACIONES,DDIG_FECHAPROCESO,USU_ID) VALUES (?,?,?,?,?,SYSDATE,?)";
        try (PreparedStatement pst = conex.prepareStatement(sql, new String[]{"DDIG_ID"})) {
            pst.setString(1, cendocumentosdigitalizado.getNombre());
            pst.setString(2, cendocumentosdigitalizado.getRuta());
            pst.setInt(3, cendocumentosdigitalizado.getTipo());
            pst.setInt(4, cendocumentosdigitalizado.getReferencia_id());
            pst.setString(5, cendocumentosdigitalizado.getObservacion());
            pst.setInt(6, cendocumentosdigitalizado.getUsu_id());
            pst.executeUpdate();
            try (ResultSet rst = pst.getGeneratedKeys()) {
                if (rst != null) {
                    if (rst.next()) {
                        return rst.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarCenso: " + e);
        }
        return 0;
    }

    public List ListarDocumentosDigitalizados(Connection conex, int idcenso) throws SQLException {

        LinkedList listaDocumentos = new LinkedList();

        String sql = "SELECT * FROM CEN_DOCUMENTOS_DIGITALIZADOS WHERE DDIG_REFERENCIA = ? ORDER BY 1";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, idcenso);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    listaDocumentos.add(CenDocumentosDigitalizado.load(rst));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarDocumentosDigitalizados: " + e);
        }
        return listaDocumentos;
    }

    public List ConvertirPDFtoImagenes(String ruta) {
        LinkedList listar_imagenes = new LinkedList();
        String b64 = "";
        try {
            PDDocument document = null;
            //se carga el documento
            document = PDDocument.load(new File(ruta));

            int numero_paginas = document.getNumberOfPages();
            List pages = document.getDocumentCatalog().getAllPages();
            for (int i = 0; i <= numero_paginas - 1; i++) {
                PDPage page = (PDPage) pages.get(i);
                BufferedImage image = page.convertToImage();
                int w = (int) document.getPageFormat(i).getWidth();
                int h = (int) document.getPageFormat(i).getHeight();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedImage escala = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = escala.createGraphics();
                graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                graphics2D.drawImage(image, 0, 0, 500, 500, null);
                ImageIO.write(escala, "jpg", baos);
                baos.flush();
                byte[] imageInByteArray = baos.toByteArray();
                baos.close();

                b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
                listar_imagenes.add(b64);
                System.out.println("imagen [" + i + "] creada");
            }
            document.close();
        } catch (IOException ex) {

            System.out.println("Atencion no se pudo cargar el PDF " + ex.getMessage());
        }
        return listar_imagenes;
    }

    public CenDocumentosDigitalizado ConsultarDocumentoDigitalizadoById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_DOCUMENTOS_DIGITALIZADOS WHERE DDIG_ID = ? ORDER BY 1";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try(ResultSet rst = pst.executeQuery()){
            while (rst.next()) {
                return CenDocumentosDigitalizado.load(rst);
            }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDocumentoDigitalizadoById: " + e);
        }
        return null;
    }

    public void writeTo(InputStream in, OutputStream out) throws IOException {
        try {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }

        return;
    }

    public CenDocumentosDigitalizado ConsultarDocumentoDigitalizadoByIdCensoNombre(Connection conex, int idcenso, String nombreImagen) throws SQLException {

        String sql = "SELECT * FROM CEN_DOCUMENTOS_DIGITALIZADOS WHERE DDIG_REFERENCIA = ? AND DDIG_NOMBRE = ? ORDER BY 1";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, idcenso);
            pst.setString(2, nombreImagen);
            try (ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                return CenDocumentosDigitalizado.load(rst);
            }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDocumentoDigitalizadoByIdCensoNombre: " + e);
        }
        return null;
    }

    public void eliminarDocumentoById(Connection conex, int id) throws SQLException, IOException {

        String sql = "DELETE CEN_DOCUMENTOS_DIGITALIZADOS WHERE DDIG_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error en eliminarDocumentoById: " + e);
        }
    }

}
