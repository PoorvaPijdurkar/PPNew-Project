import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegionWiseSplitChartComponent } from 'app/region-wise-split-chart/region-wise-split-chart.component';
import { SkuAnalysisComponent } from './sku-analysis.component';

@NgModule({
  declarations: [SkuAnalysisComponent, RegionWiseSplitChartComponent],
  imports: [CommonModule],
})
export class SkuAnalysisModule {}
