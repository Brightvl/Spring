package brightvl.spring.example.tax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class) //2 вариант
class TaxCalculatorTest {

//  @Mock // нужен когда мы хотим изоолировать тестируемую часть
//  TaxResolver mock; //2 вариант

  @Test
  void testGetPriceWithTax() {
    TaxResolver mock = mock(TaxResolver.class); // подменяем реальный объект
//    when(mock.getCurrentTax()).thenReturn(0.2); // 2 вариант  в момент вызова метода мы возвращаем 0.2
    doReturn(0.2).when(mock).getCurrentTax(); // void метод лучше mock так

    TaxCalculator taxCalculator = new TaxCalculator(mock);
    assertEquals(120.0, taxCalculator.getPriceWithTax(100.0)); // assert все равно нужен

    verify(mock).getCurrentTax();
  }

}