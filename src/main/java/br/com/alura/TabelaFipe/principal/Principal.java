package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        var menu = """
                *** OPÇÕES ***
                1 - Carro
                2 - Moto
                3 - Caminhão
                Digite uma das opções para consulta:
                """;

        System.out.println(menu);
        var opcao = leitura.nextInt();
        var endereco = URL_BASE;

        if (opcao == 1){
             endereco = URL_BASE + "carros/marcas";
        } else if (opcao == 2) {
             endereco = URL_BASE + "motos/marcas";
        } else if (opcao == 3) {
             endereco = URL_BASE + "caminhoes/marcas";
        }

        System.out.println(endereco);
        var json = consumo.obterDados(endereco);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream().sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite um código referente a marca!");
        var codigoMarca = leitura.nextInt();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        System.out.println(endereco);
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("Modelos dessa Marca: \n");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
    }
}
