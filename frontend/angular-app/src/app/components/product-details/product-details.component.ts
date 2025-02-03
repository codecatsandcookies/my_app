import { Component, OnInit } from '@angular/core';
import { CartItem } from '../../common/cart-item';
import { Product } from '../../common/product';
import { ActivatedRoute } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  product: Product | null = null; // Ensure it is properly declared

  constructor(private productService: ProductService,
              private cartService: CartService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleProductDetails();
    })
  }

  handleProductDetails() {
    // get the "id" param string. convert string to a number using "+"
    const theProductId: number = Number(this.route.snapshot.paramMap.get('id'));

    if (!isNaN(theProductId)) {
      this.productService.getProduct(theProductId).subscribe(
        data => {
          this.product = data;
        }
      );
    }
  }

  addToCart() {
    if (this.product) {
      const cartItem = new CartItem({
        id: this.product.id!,
        name: this.product.name!,
        imageUrl: this.product.imageUrl!,
        unitPrice: this.product.unitPrice!,
        unitsInStock: this.product.unitsInStock!
      });
      this.cartService.addToCart(cartItem);
    }
  }

}