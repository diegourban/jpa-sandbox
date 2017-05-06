package urban.sandbox.jpa.model;

import org.junit.Before;
import org.junit.Test;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MovimentacaoTest {
    
    @Before
    public void before() {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        Categoria categoria1 = new Categoria("viagem");
        Categoria categoria2 = new Categoria("negócios");

        Conta conta = new Conta();
        conta.setTitular("Diego");
        conta.setBanco("Banco do Brasil");
        conta.setAgencia("123");
        conta.setNumero("456");

        Movimentacao movimentacao1 = new Movimentacao();
        movimentacao1.setData(Calendar.getInstance());
        movimentacao1.setDescricao("Viagem a são paulo");
        movimentacao1.setTipo(TipoMovimentacao.SAIDA);
        movimentacao1.setValor(new BigDecimal("100.00"));
        movimentacao1.setCategorias(Arrays.asList(categoria1, categoria2));
        movimentacao1.setConta(conta);

        Movimentacao movimentacao2 = new Movimentacao();
        movimentacao2.setData(Calendar.getInstance());
        movimentacao2.setDescricao("Viagem ao rio de janeiro");
        movimentacao2.setTipo(TipoMovimentacao.SAIDA);
        movimentacao2.setValor(new BigDecimal("300.00"));
        movimentacao2.setCategorias(Arrays.asList(categoria1, categoria2));
        movimentacao1.setConta(conta);

        em.persist(conta);
        em.persist(categoria1);
        em.persist(categoria2);
        em.persist(movimentacao1);
        em.persist(movimentacao2);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void devePersistirMovimentacao() {
        // transient
        Conta conta = new Conta();
        conta.setTitular("Diego");
        conta.setBanco("Banco do Brasil");
        conta.setAgencia("123");
        conta.setNumero("456");

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setData(Calendar.getInstance());
        movimentacao.setDescricao("churrascaria");
        movimentacao.setTipo(TipoMovimentacao.SAIDA);
        movimentacao.setValor(new BigDecimal("200.00"));
        movimentacao.setConta(conta);

        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        em.persist(conta);
        em.persist(movimentacao); // managed

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void deveListarTodasAsMovimentacoes() {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        String jpql = "select distinct c from Conta c left join fetch c.movimentacoes";

        TypedQuery<Conta> query = em.createQuery(jpql, Conta.class);
        List<Conta> contas = query.getResultList();

        for(Conta c : contas) {
            System.out.println("Titular: " + c.getTitular());
            System.out.println("Movimentações: ");
            System.out.println(c.getMovimentacoes());
        }

        em.getTransaction().commit();
        em.close();
    }
}
