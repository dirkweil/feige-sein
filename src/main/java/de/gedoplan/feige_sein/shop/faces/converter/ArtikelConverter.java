package de.gedoplan.feige_sein.shop.faces.converter;

import de.gedoplan.feige_sein.shop.persistence.Artikel;
import de.gedoplan.feige_sein.shop.persistence.ArtikelRepository;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.provider.BeanProvider;

@FacesConverter(forClass = Artikel.class)
public class ArtikelConverter implements Converter
{
  @Inject
  ArtikelRepository artikelRepository;

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value)
  {
    if (value == null || value.isEmpty())
    {
      return null;
    }

    /*
     * Die Injektion von CDI Beans in Faces Converter ist leider nicht in JSF 2.2 enthalten. Mojarra 2.2.2 unterstützt
     * @Inject schon in einigen Fällen. Falls die Injektion nicht durchgeführt wurde, Bean per DeltaSpike BeanProvider holen. 
     */
    if (this.artikelRepository == null)
    {
      this.artikelRepository = BeanProvider.getContextualReference(ArtikelRepository.class);
    }

    try
    {
      Artikel artikel = this.artikelRepository.findById(Long.valueOf(value));
      if (artikel != null)
      {
        return artikel;
      }
    }
    catch (Exception e)
    {
    }

    FacesMessage msg = new FacesMessage("Unbekannter Artikel: " + value);
    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    throw new ConverterException(msg);
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value)
  {
    if (value instanceof Artikel)
    {
      Artikel artikel = (Artikel) value;
      if (artikel.getId() != null)
      {
        return artikel.getId().toString();
      }
    }

    return "";
  }

}
