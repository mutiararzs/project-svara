function togglePlaceholder() {

    const input =
        document.getElementById("tanggal");

    const placeholder =
        document.getElementById("placeholderTanggal");

    if (input.value) {

        placeholder.style.display = "none";

    } else {

        placeholder.style.display = "block";

    }

}

tailwind.config = {
    darkMode: "class",
    theme: {
        extend: {
            "colors": {
                "svara-purple": "#2e0556",
                "svara-blue": "#0f172a",
                "info-blue": "#3b82f6",
                "warning-amber": "#fbbf24",
                "error-rose": "#fb7185",
                "success-emerald": "#34d399",
                "background-dark": "#191022",
                "glass-nav": "rgba(15, 23, 42, 0.85)",
                "glass-border": "rgba(255, 255, 255, 0.08)",
                "glass-surface": "rgba(255, 255, 255, 0.03)",
                // Warna khusus dari regist.html
                "primary": "#7f13ec",
                "secondary": "#3b82f6",
                "card-dark": "#1f182b",
                "input-dark": "#2a2139"
            },
            "borderRadius": {
                "DEFAULT": "1rem",
                "lg": "1.5rem",
                "xl": "2rem",
                "2xl": "2.5rem",
                "full": "9999px"
            },
            "spacing": {
                "container-padding": "1.5rem",
                "stack-gap-md": "0.75rem",
                "nav-height": "5rem",
                "card-gap": "1rem",
                "stack-gap-sm": "0.25rem",
                "section-margin": "1.5rem"
            },
            "fontFamily": {
                "display": ["Plus Jakarta Sans", "sans-serif"],
                "headline-md": ["Inter"],
                "stats-number": ["Inter"],
                "nav-label": ["Inter"],
                "title-sm": ["Inter"],
                "display-lg": ["Inter"],
                "label-caps": ["Inter"],
                "body-md": ["Noto Sans"]
            },
            "fontSize": {
                "headline-md": ["20px", { "lineHeight": "28px", "fontWeight": "700" }],
                "stats-number": ["24px", { "lineHeight": "32px", "fontWeight": "700" }],
                "nav-label": ["10px", { "lineHeight": "12px", "fontWeight": "500" }],
                "title-sm": ["18px", { "lineHeight": "24px", "fontWeight": "700" }],
                "display-lg": ["30px", { "lineHeight": "1.2", "letterSpacing": "-0.02em", "fontWeight": "700" }],
                "label-caps": ["12px", { "lineHeight": "16px", "letterSpacing": "0.05em", "fontWeight": "600" }],
                "body-md": ["14px", { "lineHeight": "20px", "fontWeight": "400" }]
            },
            "backgroundImage": {
                'brand-gradient': 'linear-gradient(135deg, #0f172a 0%, #1e1b4b 50%, #3b0764 100%)',
                'logo-gradient': 'linear-gradient(to right, #60a5fa, #c084fc)',
                'btn-gradient': 'linear-gradient(90deg, #7f13ec 0%, #6366f1 100%)',
            }
        }
    }
}
