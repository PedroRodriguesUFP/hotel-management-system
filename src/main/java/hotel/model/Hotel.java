package hotel.model;

import hotel.model.pessoas.Cliente;
import hotel.model.pessoas.Funcionario;
import hotel.model.quartos.Quarto;
import hotel.model.reservas.Reserva;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String nome;
    private List<Quarto> quartos;
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;
    private List<Reserva> reservas;

    public Hotel(String nome) {
        this.nome = nome;
        this.quartos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public String getNome() { return nome; }

    public List<Quarto> getQuartos() { return quartos; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Funcionario> getFuncionarios() { return funcionarios; }
    public List<Reserva> getReservas() { return reservas; }

    public void adicionarQuarto(Quarto q) { quartos.add(q); }
    public void adicionarCliente(Cliente c) { clientes.add(c); }
    public void adicionarFuncionario(Funcionario f) { funcionarios.add(f); }
    public void adicionarReserva(Reserva r) { reservas.add(r); }
}