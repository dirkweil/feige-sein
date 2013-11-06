package de.gedoplan.feige_sein.test.persistence;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 1)
@Transactional
public class TestTransactionInterceptor
{
  @Inject
  EntityManager entityManager;

  @AroundInvoke
  Object manageTransaction(InvocationContext invocationContext) throws Exception
  {
    if (this.entityManager.getTransaction().isActive())
    {
      return invocationContext.proceed();
    }

    this.entityManager.getTransaction().begin();

    try
    {
      Object result = invocationContext.proceed();
      this.entityManager.getTransaction().commit();
      return result;
    }
    catch (Exception exception)
    {
      this.entityManager.getTransaction().rollback();
      throw exception;
    }
  }

}
