// These constants are injected via webpack DefinePlugin variables.
// You can add more variables in webpack.common.js or in profile specific webpack.<dev|prod>.js files.
// If you change the values in the webpack config files, you need to re run webpack to update the application

declare const __DEBUG_INFO_ENABLED__: boolean;
declare const __VERSION__: string;

export const VERSION = __VERSION__;
export const DEBUG_INFO_ENABLED = __DEBUG_INFO_ENABLED__;

export const PRODUCT_TYPES = [
  { code: 'Raw Materials', name: 'Raw Materials' },
  { code: 'Finished-Goods', name: 'Finished-Goods' },
];

export const UOMS = [
  { code: 'KG', name: 'Kg' },
  { code: 'LITER', name: 'Liter' },
  { code: 'MT', name: 'MT' },
  { code: 'Nos', name: 'Nos' },
];
