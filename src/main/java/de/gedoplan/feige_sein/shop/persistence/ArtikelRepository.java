package de.gedoplan.feige_sein.shop.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;

import javax.transaction.Transactional;

@Transactional
public class ArtikelRepository extends SingleIdEntityRepository<Long, Artikel>
{
  private static final long serialVersionUID = 1L;
}
