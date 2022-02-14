package com.dev.loja.controle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.dev.loja.modelos.Funcionario;
import com.dev.loja.repositorios.FuncionarioRepositorio;
import com.dev.loja.servico.EnviarEmailServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class RelatorioControle {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/administrativo/relatorio")
    public void gerarRelatorio(HttpServletResponse response) throws JRException, SQLException, IOException {
        InputStream jasperFile = this.getClass().getResourceAsStream("/relatorios/teste.jasper");

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=relatorio.pdf");

        OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @GetMapping("/administrativo/relatorioProduto")
    public void gerarRelatorioProduto(HttpServletResponse response) throws JRException, SQLException, IOException {
        InputStream jasperFile = this.getClass().getResourceAsStream("/relatorios/RelatorioProduto.jasper");

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=relatorio_produto.pdf");

        OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @GetMapping("/administrativo/relatorioVendas")
    public void gerarRelatorioVendas(HttpServletResponse response) throws JRException, SQLException, IOException {
        InputStream jasperFile = this.getClass().getResourceAsStream("/relatorios/RelatorioVendas.jasper");

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=relatorio_vendas.pdf");

        OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

}
