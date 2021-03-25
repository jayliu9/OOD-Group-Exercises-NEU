package problem;

public class Main {
    public static void main(String[] args)
        throws InvalidCommissionRateException, InvalidTotalEarningsException, ListingNotFoundException, InvalidNumOfOfficesException, InvalidSizeException, InvalidNumOfBathroomsException, InvalidNumOfBedroomsException, InvalidTermException, InvalidAskingPriceException {
      Residential r1 = new Residential("No.1001", 90, 2, 1.5);
      Residential r2 = new Residential("No.1002", 100, 3, 2.0);

      Commercial c1 =  new Commercial("Building 8", 50, 1, false);
      Commercial c2 =  new Commercial("Building 13", 72, 1, true);

      Sale s1 = new Sale(2000000.0, true);
      Sale s2 = new Sale(1000000.0, false);

      Rental ren1 = new Rental(1500.0, false, 12);
      Rental ren2 = new Rental(3000.0, true, 12);

      Listing<Residential, Sale> listing1 = new Listing<>(r1, s1);
      Listing<Residential, Sale> listing2 = new Listing<>(r2, s2);
      Listing<Commercial, Rental> listing3 = new Listing<>(c1, ren1);
      Listing<Residential, Rental> listing4 = new Listing<>(r2, ren2);
      Listing<Commercial, Sale> listing5 = new Listing<>(c2, s2);

      // lucy only take on problem.Residential with problem.Sale contract
      Agent<Listing<Residential, Sale>> lucy = new Agent<>("lucy", 0.05, 30000.0);
      lucy.addListing(listing1);
      lucy.addListing(listing2);


      // jimmy only take on problem.Commercial with problem.Rental contract
      Agent<Listing<Commercial, Rental>> jimmy = new Agent<>("jimmy", 0.06, 50000.0);
      jimmy.addListing(listing3);

      for (Listing listing: lucy.currentListings) {
        System.out.println(listing.toString());
      }

      // fiona take on problem.Residential with any type Contract
      Agent<Listing<Residential, ?>> fiona = new Agent<>("fiona", 0.07, 85000.0);
      fiona.addListing(listing1);
      fiona.addListing(listing2);
      fiona.addListing(listing4);
      System.out.println(fiona.getTotalPortfolioValue());

      // peter take on any type property with problem.Sale Contract
      Agent<Listing<?, Sale>> peter = new Agent<>("fiona", 0.044, 55000.0);
      peter.addListing(listing1);
      peter.addListing(listing2);
      peter.addListing(listing5);

      // champion take any type problem.Residential with any type Contract
      Agent<Listing> champion = new Agent<>("champion", 0.056, 100000.0);
      champion.addListing(listing1);
      champion.addListing(listing3);
      champion.addListing(listing4);
      champion.addListing(listing5);

      champion.dropListing(listing3);
      champion.completeListing(listing3);
    }
}
