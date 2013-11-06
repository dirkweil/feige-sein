package de.gedoplan.feige_sein.waehrung.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;

import javax.transaction.Transactional;

@Transactional
public class WaehrungRepository extends SingleIdEntityRepository<String, Waehrung>
{
  private static final long serialVersionUID = 1L;
}
