package de.gedoplan.feige_sein.waehrung.webservice;

import de.gedoplan.feige_sein.waehrung.persistence.Waehrung;
import de.gedoplan.feige_sein.waehrung.persistence.WaehrungRepository;
import de.gedoplan.feige_sein.waehrung.service.WaehrungService;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

@Path("waehrung")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class WaehrungRestService {
  @Inject
  WaehrungRepository waehrungRepository;

  @Inject
  WaehrungService waehrungService;

  @GET
  public List<Waehrung> getAll() {
    return this.waehrungRepository.findAll();
  }

  @GET
  @Path("{id}")
  public Waehrung getById(@PathParam("id") String id) {
    Waehrung waehrung = this.waehrungRepository.findById(id);
    if (waehrung != null) {
      return waehrung;
    }

    throw new WebApplicationException(Status.NOT_FOUND);
  }

  @GET
  @Path("{id}/{amount}")
  @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public BigDecimal getEuro(@PathParam("id") String id, @PathParam("amount") BigDecimal amount) {
    return this.waehrungService.umrechnen(amount, id);
  }

}
