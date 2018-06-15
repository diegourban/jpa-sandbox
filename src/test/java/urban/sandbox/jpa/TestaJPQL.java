package urban.sandbox.jpa;


import org.junit.Before;
import org.junit.Test;
import urban.sandbox.jpa.model.Categoria;
import urban.sandbox.jpa.model.Conta;
import urban.sandbox.jpa.model.Movimentacao;
import urban.sandbox.jpa.model.TipoMovimentacao;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TestaJPQL {

    @Before
    public void popular() {
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
        movimentacao2.setTipo(TipoMovimentacao.ENTRADA);
        movimentacao2.setValor(new BigDecimal("300.00"));
        movimentacao2.setCategorias(Arrays.asList(categoria1, categoria2));
        movimentacao2.setConta(conta);

        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        em.persist(conta);
        em.persist(categoria1);
        em.persist(categoria2);
        em.persist(movimentacao1);
        em.persist(movimentacao2);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void teste1() {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        Conta conta = em.find(Conta.class, 1);

        String jpql = "select m from Movimentacao m where m.conta = :pConta and m.tipo = :pTipo";
        TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
        query.setParameter("pConta", conta);
        query.setParameter("pTipo", TipoMovimentacao.ENTRADA);
        List<Movimentacao> movimentacoes = query.getResultList();

        for(Movimentacao m : movimentacoes) {
            System.out.println("Descrição: " + m.getDescricao());
            System.out.println("Conta.id: " + m.getConta().getId());
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void teste2() {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        Categoria categoria = em.find(Categoria.class, 1);

        String jpql = "select m from Movimentacao m join m.categorias c where c = :pCategoria";
        TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
        query.setParameter("pCategoria", categoria);
        List<Movimentacao> movimentacoes = query.getResultList();

        for(Movimentacao m : movimentacoes) {
            System.out.println("Descrição: " + m.getDescricao());
            System.out.println("Conta.id: " + m.getConta().getId());
        }

        em.getTransaction().commit();
        em.close();
    }
}
