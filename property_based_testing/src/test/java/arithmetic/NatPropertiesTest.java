package arithmetic;

import com.pholser.junit.quickcheck.generator.Size;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assume.assumeTrue;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitQuickcheck.class)
public class NatPropertiesTest {
    /*
      Reflexive Property
      a == a
    */
    @Property
    public void numberEqualsSelf(@From(NatGenerator.class) @Size(max = 10) final Nat a) {
        assertEquals(a, a);
    }

    /*
      Zero Product Property
      a * 0 = 0
    */
    @Property
    public void numberMultipliedByZeroEqualsZero(
        @From(NatGenerator.class) @Size(max = 10) final Nat a,
        @From(NatGenerator.class) @Size(min = 0, max = 0) final Nat zero
    ) {
        assertTrue((a.multiply(zero)).isZero());
    }

    /*
      Identity Property of Addition
      a + 0 = a = 0 + a
    */
    @Property
    public void identityPropertyOfAddition(
        @From(NatGenerator.class) @Size(max = 10) final Nat a,
        @From(NatGenerator.class) @Size(min = 0, max = 0) final Nat zero
    ) {
        assertTrue(zero.isZero());
        assertEquals(a.add(zero), a);
        assertEquals(a, a.add(zero));
        assertEquals(a.add(zero), a.add(zero));
    }

    /*
      Identity Property of Multiplication
      a * 1 = a = 1 * a
    */
    @Property
    public void identityPropertyOfMultiplication(
        @From(NatGenerator.class) @Size(max = 10) final Nat a,
        @From(NatGenerator.class) @Size(min = 1, max = 1) final Nat one
    ) {
        assertTrue(one.isOne());
        assertEquals(a.multiply(one), a);
        assertEquals(a, a.multiply(one));
    }

    /*
      Inverse Property
      a + (-a) = 0 = (-a) + a
    */
    @Property
    public void inverseProperty(
        @From(NatGenerator.class) @Size(min = 5, max = 5) final Nat a,
        @From(NatGenerator.class) @Size(min = 0, max = 0) final Nat zero
    ) {
        Nat neg_a = a.subtract(a).subtract(a); // a - a - a = -a
        assertEquals(a.add(neg_a), neg_a.add(a));
        assertEquals(neg_a.add(a), a.add(neg_a));
    }

    /*
      Commutative Property of Addition
      a + b == b + a
    */
    @Property
    public void commutativePropertyAddition(@From(NatGenerator.class) @Size(max = 10) final Nat a, @From(NatGenerator.class) @Size(max = 10) final Nat b) {
        assertEquals(a.add(b), b.add(a));
    }

    /*
      Commutative Property of Multiplication
      a * b == b * a
    */
    @Property
    public void commutativePropertyMultiplication(
        @From(NatGenerator.class) @Size(max = 10) final Nat a,
        @From(NatGenerator.class) @Size(max = 10) final Nat b
    ) {
        assertEquals(a.multiply(b), b.multiply(a));
    }

    /*
      Associative Property of Addition
      (a + b) + c == a + (b + c)
    */
    @Property
    public void associativePropertyAddition(
        @From(NatGenerator.class) @Size(max = 10) final Nat a,
        @From(NatGenerator.class) @Size(max = 10) final Nat b,
        @From(NatGenerator.class) @Size(max = 10) final Nat c
    ) {
        assertEquals((a.add(b)).add(c), a.add(b.add(c)));
    }

    /*
      Associative Property of Multiplication
      (a * b) * c == a * (b * c)
    */
    @Property
    public void associativePropertyMultiplication(
        @From(NatGenerator.class) @Size(max = 10) final Nat a,
        @From(NatGenerator.class) @Size(max = 10) final Nat b,
        @From(NatGenerator.class) @Size(max = 10) final Nat c
    ) {
        assertEquals((a.multiply(b)).multiply(c), a.multiply(b.multiply(c)));
    }

    /*
      a * (b + c) = a * b + a * c
    */
    @Property
    public void distributiveProperty(
        @From(NatGenerator.class) @Size(max = 10) final Nat a,
        @From(NatGenerator.class) @Size(max = 10) final Nat b,
        @From(NatGenerator.class) @Size(max = 10) final Nat c
    ) {
        assertEquals(a.multiply(b.add(c)), (a.multiply(b)).add(a.multiply(c)));
    }

    /*
      a - a = 0
    */
    @Property
    public void numberMinusItselfIsZero(
        @From(NatGenerator.class) @Size(max = 10) final Nat a
    ) {
        assertTrue(a.subtract(a).isZero());
    }

    /*
      a + a == 2 * a
    */
    @Property
    public void SumOfNumberWithItselfEqualsNumberMulipliedByTwo(
        @From(NatGenerator.class) @Size(max = 10) final Nat a,
        @From(NatGenerator.class) @Size(min = 2, max = 2) final Nat two
    ) {
        assertEquals(a.add(a), a.multiply(two));
    }

    /*
      { (a + a + ...) } b times == a * b
      { (b + b + ...) } a times == b * a
      { (a + a + ...) } b times == { (b + b + ...) } a times
    */
    @Property
    public void sumOfNumberWithItselfNTimesEqualsNumberMultipliedByN(
        @From(NatGenerator.class) @Size(min = 1, max = 10) final Nat a,
        @From(NatGenerator.class) @Size(min = 1, max = 10) final Nat b
    ) {
        int count_a = (int)(a.toString().chars().filter(ch -> ch == 'S').count());
        int count_b = (int)(b.toString().chars().filter(ch -> ch == 'S').count());

        Nat a_copy = a;
        Nat b_copy = b;

        for( int i = 1; i < count_b; i++ ){
            a_copy = a_copy.add(a);
        }

        for( int i = 1; i < count_a; i++ ){
            b_copy = b_copy.add(b);
        }

        assertEquals(a_copy, a.multiply(b));
        assertEquals(b_copy, b.multiply(a));
        assertEquals(a_copy, b_copy);
    }

    // TODO: write your properties below
    // Put a comment before them explaining what sort of property you're testing
}
