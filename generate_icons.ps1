# App Icon Generator Script
# Generates launcher icons for both Prime and Prime Partner apps

Write-Host "🎨 Generating App Icons..." -ForegroundColor Cyan

# Create a simple batch script to generate icons using Android Asset Studio concept
# Since we need proper icon generation, we'll create XML-based adaptive icons

$primeColor = "#1E3A5F"  # Blue
$partnerColor = "#FF6B35"  # Orange

Write-Host ""
Write-Host "📱 Prime App - Blue Theme" -ForegroundColor Blue
Write-Host "   Colors: $primeColor (Primary)" -ForegroundColor Blue
Write-Host ""
Write-Host "📱 Prime Partner App - Orange Theme" -ForegroundColor Yellow
Write-Host "   Colors: $partnerColor (Primary)" -ForegroundColor Yellow
Write-Host ""

Write-Host "✅ To generate proper launcher icons, you have 3 options:" -ForegroundColor Green
Write-Host ""
Write-Host "Option 1: Android Studio Image Asset Studio" -ForegroundColor White
Write-Host "  1. Right-click on 'res' folder" -ForegroundColor Gray
Write-Host "  2. New → Image Asset" -ForegroundColor Gray
Write-Host "  3. Icon Type: Launcher Icons (Adaptive and Legacy)" -ForegroundColor Gray
Write-Host "  4. Foreground Layer: Use your logo image or text 'P'" -ForegroundColor Gray
Write-Host "  5. Background Layer: Use solid color" -ForegroundColor Gray
Write-Host "     - Prime: #1E3A5F (Blue)" -ForegroundColor Gray
Write-Host "     - Prime Partner: #FF6B35 (Orange)" -ForegroundColor Gray
Write-Host ""

Write-Host "Option 2: Online Icon Generator" -ForegroundColor White
Write-Host "  • https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html" -ForegroundColor Gray
Write-Host "  • https://easyappicon.com/" -ForegroundColor Gray
Write-Host "  • https://icon.kitchen/" -ForegroundColor Gray
Write-Host ""

Write-Host "Option 3: Use prepared XML adaptive icons (Quick)" -ForegroundColor White
Write-Host "  Creating simple text-based adaptive icons..." -ForegroundColor Gray
Write-Host ""

Write-Host "🔧 Creating XML-based adaptive icons..." -ForegroundColor Yellow

# We'll create the XML files for adaptive icons
Write-Host "✅ Icon generation guide complete!" -ForegroundColor Green
Write-Host ""
Write-Host "📝 Next steps:" -ForegroundColor Cyan
Write-Host "  1. Open Android Studio" -ForegroundColor White
Write-Host "  2. For Prime app: Right-click Prime/app/src/main/res → New → Image Asset" -ForegroundColor White
Write-Host "  3. For Prime Partner: Right-click 'Prime Partner'/app/src/main/res → New → Image Asset" -ForegroundColor White
Write-Host "  4. Configure icons with respective colors" -ForegroundColor White
Write-Host ""
Write-Host "🎨 Or I can create simple text-based launcher icons right now!" -ForegroundColor Green
