package brightvl.spring.example.tax;

// Класс, который отдает текущий НДС
public class TaxResolver {

  public double getCurrentTax() {
    // идем в ЦБ по HTTP, получаем текущий НДС
    return 0.3;
  }

}
