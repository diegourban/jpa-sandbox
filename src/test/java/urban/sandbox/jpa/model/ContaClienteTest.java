package urban.sandbox.jpa.model;

import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;

public class ContaClienteTest {

    public void devePersistir() {
        Cliente cliente = new Cliente();
        cliente.setNome("Diego");
        cliente.setEndereco("Rua xpto");
        cliente.setProfissao("programador");

        Conta conta = new Conta();
        conta.setTitular("Diego");
        conta.setBanco("Banco do Brasil");
        conta.setAgencia("123");
        conta.setNumero("456");

        cliente.setConta(conta);

        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        em.persist(conta);
        em.persist(cliente);

        em.getTransaction().commit();
        em.close();
    }
}
